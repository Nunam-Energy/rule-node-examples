/**
 * Copyright © 2018 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.rule.engine.node.external;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProducerRecordCreator {

	private static final Logger logger = Logger.getLogger(ProducerRecordCreator.class);

	private List<ProducerRecord<String, String>> records = new ArrayList<>();
	private Message message;

	private static final String TOPIC_NAME_PREFIX = "qmax.tb.battery.";

	private static final ObjectMapper MAPPER;
	private static final TempStringVoltages TEMP_STRING_VOLTAGES;
	private static final TempStringVoltages TEMP_TEMP_SERIES;

	private static Map<String, String> keyMap = new HashMap<>();
	private static Map<String, Integer> partitionMap = new HashMap<>();
	private static Map<String, Integer> positionMap = new HashMap<>();

	private static TransformerService transformerService = new TransformerService();

	static {
		setKeyMap();
		MAPPER = new ObjectMapper();
		TEMP_STRING_VOLTAGES = TempStringVoltages.getTempStringVoltages();
		TEMP_TEMP_SERIES = TempStringVoltages.getTempStringVoltages();
		partitionMap.put("ic", 1);
		partitionMap.put("lv", 2);
		partitionMap.put("soc", 3);
		partitionMap.put("strv", 4);
		partitionMap.put("tmp", 5);
		partitionMap.put("tmpSeries", 6);
		partitionMap.put("capacityDischarge", 7);
		System.out.println(keyMap);
		logger.info("Custom Node: Map Initiated");
	}

	public ProducerRecordCreator(Message message) {
		this.message = message;
	}

	public void process() throws Exception {

		Field[] fields = Message.class.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);
			Object val = field.get(this.message);
			if (val == null) {
				continue;
			}
			String kafkaKey = getKafkaKey(field.getName());
			if (kafkaKey == null) {
				continue;
			}

			String deviceId = this.message.getDeviceId();
			Integer modulePosition = 1;
			if(deviceId.equals("897d1ee0-327e-11ed-8506-f961a5b87d75") || deviceId.equals("140cd230-0e3a-11ed-9bda-0163f5a9393b")) {
				modulePosition = getModulePosition(field.getName());
			}else {
				modulePosition = getPositionFromModule(deviceId);
			}
			
			Integer customerId = transformerService.getCustomerId(deviceId);
			if (customerId == null) {
				logger.error("could not find customer ID of device: " + deviceId);
				return;
			}

			if (kafkaKey.equals("strv")) {
				Integer stringIndex = getStringIndexFromKey(field.getName());

				KafkaStringVoltageMessage msg = TEMP_STRING_VOLTAGES.check(
						this.message.getDeviceId() + "." + modulePosition, this.message.getTs(), stringIndex,
						(String) val);
				if (msg != null) {
					String kafkaJson = MAPPER.writeValueAsString(msg);
					ProducerRecord<String, String> record = new ProducerRecord<String, String>(
							TOPIC_NAME_PREFIX + deviceId + "." + modulePosition + "." + customerId,
							partitionMap.get("strv"), "strv", kafkaJson);
					this.records.add(record);
				}
			} else if (kafkaKey.equals("tmpSeries")) {

				Integer stringIndex = getStringIndexFromKey(field.getName());

				KafkaStringVoltageMessage msg = TEMP_STRING_VOLTAGES.check(
						this.message.getDeviceId() + "." + modulePosition, this.message.getTs(), stringIndex,
						(String) val);
				if (msg != null) {
					String kafkaJson = MAPPER.writeValueAsString(msg);
					ProducerRecord<String, String> record = new ProducerRecord<String, String>(
							TOPIC_NAME_PREFIX + this.message.getDeviceId() + "." + modulePosition + "." + customerId,
							partitionMap.get("tmpSeries"), "tmpSeries", kafkaJson);
					this.records.add(record);
				}

			} else {
				String value = (String) val;
				KafkaMessage kafkaMessage = new KafkaMessage();
				kafkaMessage.setValue(value);
				kafkaMessage.setTs(this.message.getTs());
				String kafkaJson = MAPPER.writeValueAsString(kafkaMessage);
				ProducerRecord<String, String> record = new ProducerRecord<String, String>(
						TOPIC_NAME_PREFIX + this.message.getDeviceId() + "." + modulePosition + "." + customerId,
						partitionMap.get(kafkaKey), kafkaKey, kafkaJson);

				this.records.add(record);
			}

		}

	}

	private String getKafkaKey(String fieldName) {
		for (String key : keyMap.keySet()) {
			if (fieldName.matches(key)) {
				return keyMap.get(key);
			}
		}
		return null;
	}

	private Integer getStringIndexFromKey(String key) {
		String[] nums = key.split("[^0-9]");
		if (nums.length > 0) {
			return Integer.parseInt(nums[nums.length - 1]);
		} else {
			return -1;
		}
	}

	/**
	 * Sets value to KeyMap with matchable regex with all possible key combnations
	 */
	private static void setKeyMap() {

		try {

			Map<String, List<String>> keyMapT = new HashMap<>();
			String keysJson = getKeysFromTransformer();
			ObjectMapper mapper = new ObjectMapper();
			List<KeyMap> keys = mapper.readValue(keysJson, new TypeReference<List<KeyMap>>() {
			});
			for (KeyMap key : keys) {
				putKeys("ic", key.getIc(), keyMapT);
				putKeys("ic", key.getId(), keyMapT);
				putKeys("lv", key.getLv(), keyMapT);
				putKeys("soc", key.getSoc(), keyMapT);
				putKeys("tmp", key.getTmp(), keyMapT);
				putKeys("strv", key.getStrV(), keyMapT);
				// putKeys("tmpSeries", key.getTmpSeries(), keyMapT);
				putKeys("capacity_discharge", key.getCapacityDischarge(), keyMapT);
			}
			for (String key : keyMapT.keySet()) {
				for (String deviceKey : keyMapT.get(key)) {
					if (deviceKey == null) {
						continue;
					}
					deviceKey = deviceKey.replaceAll("\\{p\\}", "(\\\\d){1,2}");
					deviceKey = deviceKey.replaceAll("\\{i\\}", "(\\\\d){1,3}");
					keyMap.put(deviceKey, key);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Setting Map");
		}

	}

	private static void putKeys(String key, String value, Map<String, List<String>> keyMapT) {

		List<String> storedKeys = keyMapT.get(key);
		if (storedKeys == null) {
			storedKeys = new ArrayList<String>();
		}
		storedKeys.add(value);
		keyMapT.put(key, storedKeys);

	}

	private Integer getModulePosition(String keyName) {
		
		if (keyName.matches("B\\d+.+")) {
			Integer number = Integer.parseInt(keyName.split("[^0-9]+")[1]);
			return number;
		} else {
			return 1;
		}

	}

	public List<ProducerRecord<String, String>> getRecords() {
		return records;
	}

	public void setRecords(List<ProducerRecord<String, String>> records) {
		this.records = records;
	}

	/**
	 * get all possible key mappings from transformer
	 * 
	 * @return return response from transformer api as string
	 * @throws Exception
	 */
	private static String getKeysFromTransformer() throws Exception {
		URL yahoo = new URL("http://3.111.151.104:8085/api/profiles/key-map");
		URLConnection yc = yahoo.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		String inputLines = "";
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			inputLines += inputLine;
		in.close();
		return inputLines;
	}

	private static Integer getPositionFromModule(String moduleId) {
		
		Integer position = positionMap.get(moduleId);
		if (position == null) {
			try {
				URL yahoo = new URL("http://3.111.151.104:8085/api/modules/uuid/" + moduleId);
				URLConnection yc = yahoo.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
				String inputLines = "";
				String inputLine;

				while ((inputLine = in.readLine()) != null)
					inputLines += inputLine;
				in.close();
				String[] lines = inputLines.split(",");
				String[] positionSplit = lines[5].split(":");
				position = Integer.parseInt(positionSplit[1]);
			} catch (Exception e) {
				position = 1;
			}
			positionMap.put(moduleId, position);
		}
		return position;

	}

}

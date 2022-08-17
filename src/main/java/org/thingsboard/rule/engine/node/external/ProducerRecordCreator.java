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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ProducerRecordCreator {
	
	private static final Logger logger = Logger.getLogger(ProducerRecordCreator.class);

	private List<ProducerRecord<String, String>> records = new ArrayList<>();
	private Message message;
	
	
	private static final String TOPIC_NAME_PREFIX = "c1qmax.tb.battery.";
	private static final Map<String,String> keyMap = new HashMap<>();
	private static final ObjectMapper MAPPER;
	private static final TempStringVoltages TEMP_STRING_VOLTAGES; 
	
	static{
		keyMap.put("B0Q", "discharge_capacity");
		
		keyMap.put("B0SOC", "soc");
		
		keyMap.put("B0Vp", "lv");
		
		keyMap.put("B0V1", "strv_1");
		keyMap.put("B0V2", "strv_2");
		keyMap.put("B0V3", "strv_3");
		keyMap.put("B0V4", "strv_4");
		keyMap.put("B0V5", "strv_5");
		keyMap.put("B0V6", "strv_6");
		keyMap.put("B0V7", "strv_7");
		keyMap.put("B0V8", "strv_8");
		keyMap.put("B0V9", "strv_9");
		keyMap.put("B0V10", "strv_10");
		keyMap.put("B0V11", "strv_11");
		keyMap.put("B0V12", "strv_12");
		keyMap.put("B0V13", "strv_13");
		keyMap.put("B0V14", "strv_14");
		keyMap.put("B0V15", "strv_15");
		keyMap.put("B0V16", "strv_16");
		keyMap.put("B0V17", "strv_17");
		keyMap.put("B0V18", "strv_18");
		keyMap.put("B0V19", "strv_19");
		keyMap.put("B0V20", "strv_20");
		
		keyMap.put("B0I", "ic");
		
		keyMap.put("B0T1", "tmp_1");
		
		keyMap.put("B0T2", "tmp_2");
		
		MAPPER = new ObjectMapper();
		
		TEMP_STRING_VOLTAGES = TempStringVoltages.getTempStringVoltages();
		
		logger.info("Custom Node: Map Initiated");
	}
	
	public ProducerRecordCreator(Message message) {
		this.message = message;
	}
	
	public void process() throws Exception {
		
		Field[] fields = Message.class.getDeclaredFields();
		
		for(Field field:fields) {
			field.setAccessible(true);
			Object val = field.get(this.message);
			if(val==null || keyMap.get(field.getName())==null) {
				continue;
			}
			
			if(field.getName().contains("B0V") && !field.getName().equals("B0Vp")) {
				KafkaStringVoltageMessage msg = TEMP_STRING_VOLTAGES.check(this.message.getDeviceId(), this.message.getTs(), Integer.parseInt(field.getName().substring(3)), (String)val);
				if(msg!=null) {
					String kafkaJson = MAPPER.writeValueAsString(msg);
					ProducerRecord<String, String> record =  new ProducerRecord<String, String>(TOPIC_NAME_PREFIX+this.message.getDeviceId(), "strv", kafkaJson);
					this.records.add(record);
				}
			}else {
				String value = (String) val; 
				KafkaMessage kafkaMessage = new KafkaMessage();
				kafkaMessage.setValue(value);
				kafkaMessage.setTs(this.message.getTs());
				String kafkaJson = MAPPER.writeValueAsString(kafkaMessage);
				ProducerRecord<String, String> record =  new ProducerRecord<String, String>(TOPIC_NAME_PREFIX+this.message.getDeviceId(), keyMap.get(field.getName()), kafkaJson);
				this.records.add(record);
			}
			
		}
		
	}

	public List<ProducerRecord<String, String>> getRecords() {
		return records;
	}

	public void setRecords(List<ProducerRecord<String, String>> records) {
		this.records = records;
	}
	
	
}

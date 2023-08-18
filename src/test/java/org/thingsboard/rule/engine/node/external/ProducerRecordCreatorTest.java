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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProducerRecordCreatorTest {

	private static String json;
	private KeyMap[] keyMaps;
	
	@Before
	public void set() {
		this.json = "{\n" + 
				"    \"deviceId\": \"140cd230-0e3a-11ed-9bda-0163f5a9393b\",\n" + 
				"    \"ts\": \"1660657577000\",\n" + 
				"    \"B0Q\": 444,\n" +
				"    \"B0T1\": 32,\n" +
				"    \"B0T2\": 33,\n" +
				"    \"B0SOC\": 99,\n" +
				"    \"B0I\": 3000,\n" +
				"    \"B0Vp\": 5000,\n" +
				"    \"B0V1\": 1001,\n" + 
				"    \"B0V2\": 1002,\n" + 
				"    \"B0V3\": 1003,\n" + 
				"    \"B0V4\": 1004,\n" + 
				"    \"B0V5\": 1005,\n" + 
				"    \"B0V6\": 1006,\n" + 
				"    \"B0V7\": 1007,\n" + 
				"    \"B0V8\": 1008,\n" + 
				"    \"B0V9\": 1009,\n" + 
				"    \"B0V10\": 1010,\n" + 
				"    \"B0V11\": 1011,\n" + 
				"    \"B0V12\": 1012,\n" + 
				"    \"B0V13\": 1013,\n" + 
				"    \"B0V14\": 1014,\n" + 
				"    \"B0V15\": 1015,\n" + 
				"    \"B0V16\": 1016,\n" +
				"    \"B0V17\": 1017,\n" +
				"    \"B0V18\": 1018,\n" +
				"    \"B0V19\": 1019,\n" +
				"    \"B0V20\": 1020\n" +
				"}";
		this.keyMaps = new KeyMap[4];
		
		KeyMap keyMap = new KeyMap();
		keyMap.setProfileId(11);
		keyMap.setIc("B{p}I");
		keyMap.setId("B{p}I");
		keyMap.setLv("B{p}Vp");
		keyMap.setSoc("B{p}SOC");
		keyMap.setStrV("B{p}V{i}");
		keyMap.setTmp("B{p}T1");
		this.keyMaps[0] = keyMap;
		
		
		keyMap = new KeyMap();
		keyMap.setProfileId(5);
		keyMap.setIc("B{p}I");
		keyMap.setId("B{p}I");
		keyMap.setLv("B{p}Vp");
		keyMap.setSoc("B{p}SOC");
		keyMap.setStrV("B{p}V{i}");
		keyMap.setTmp("B{p}T1");
		this.keyMaps[1] = keyMap;
		
		
		keyMap = new KeyMap();
		keyMap.setProfileId(15);
		keyMap.setIc("B{p}I");
		keyMap.setId("B{p}I");
		keyMap.setLv("B{p}Vp");
		keyMap.setSoc("B{p}SOC");
		keyMap.setStrV("B{p}V{i}");
		keyMap.setTmp("B{p}T1");
		this.keyMaps[2] = keyMap;
		
		
		keyMap = new KeyMap();
		keyMap.setProfileId(17);
		keyMap.setIc("Ip");
		keyMap.setId("Ip");
		keyMap.setLv("Vp");
		keyMap.setSoc("SoC");
		keyMap.setStrV("V{i}");
		keyMap.setTmp("BT1");
		this.keyMaps[3] = keyMap;
		
	}
	
	@Test
	public void testJacksonMapping() throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Message message = mapper.readValue(json,Message.class);
		Assert.assertEquals(message.getB0V1(), "1001");
	}
	
	@Test
	public void testRecords() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		Message message = mapper.readValue(json,Message.class);
		ProducerRecordCreator creator = new ProducerRecordCreator(message);
		creator.process();
		Assert.assertEquals(6, creator.getRecords().size());
		List<ProducerRecord<String, String>> records = creator.getRecords();
		
		ProducerRecord< String, String> record = records.get(5);
		Assert.assertEquals("capacity_discharge", record.key());
		Assert.assertTrue(record.value().contains("444"));
		
		record = records.get(1);
		Assert.assertEquals("tmp", record.key());
		Assert.assertTrue(record.value().contains("32"));
		
		
		record = records.get(4);
		Assert.assertEquals("soc", record.key());
		Assert.assertTrue(record.value().contains("99"));
		
		record = records.get(0);
		Assert.assertEquals("ic", record.key());
		Assert.assertTrue(record.value().contains("3000"));
		
		record = records.get(3);
		Assert.assertEquals("lv", record.key());
		Assert.assertTrue(record.value().contains("5000"));
		
		record = records.get(2);
		Assert.assertEquals("strv", record.key());
		
			
	}
	
	@Test
	public void testRecordsModule1() throws Exception{
		
		this.json = "{\n" + 
				"    \"deviceId\": \"140cd230-0e3a-11ed-9bda-0163f5a9393b\",\n" + 
				
				"    \"ts\": \"1660657577000\",\n" + 
				
				"    \"B1Q\": 444,\n" +
				
				"    \"B1T1\": 32,\n" +
				"    \"B1T2\": 33,\n" +
				
				"    \"B1SOC\": 99,\n" +
				
				"    \"B1I\": 3000,\n" +
				
				"    \"B1Vp\": 5000,\n" +
				"    \"B1V1\": 1001,\n" + 
				"    \"B1V2\": 1002,\n" + 
				"    \"B1V3\": 1003,\n" + 
				"    \"B1V4\": 1004,\n" + 
				"    \"B1V5\": 1005,\n" + 
				"    \"B1V6\": 1006,\n" + 
				"    \"B1V7\": 1007,\n" + 
				"    \"B1V8\": 1008,\n" + 
				"    \"B1V9\": 1009,\n" + 
				"    \"B1V10\": 1010,\n" + 
				"    \"B1V11\": 1011,\n" + 
				"    \"B1V12\": 1012,\n" + 
				"    \"B1V13\": 1013,\n" + 
				"    \"B1V14\": 1014,\n" + 
				"    \"B1V15\": 1015,\n" + 
				"    \"B1V16\": 1016,\n" +
				"    \"B1V17\": 1017,\n" +
				"    \"B1V18\": 1018,\n" +
				"    \"B1V19\": 1019,\n" +
				"    \"B1V20\": 1020\n" +
				"}";
		
		ObjectMapper mapper = new ObjectMapper();
		Message message = mapper.readValue(json,Message.class);
		ProducerRecordCreator creator = new ProducerRecordCreator(message);
		creator.process();
		Assert.assertEquals(6, creator.getRecords().size());
		List<ProducerRecord<String, String>> records = creator.getRecords();
		
		ProducerRecord< String, String> record = records.get(5);
		Assert.assertEquals("capacity_discharge", record.key());
		Assert.assertTrue(record.value().contains("444"));
		
		record = records.get(1);
		Assert.assertEquals("tmp", record.key());
		Assert.assertTrue(record.value().contains("32"));
		
		
		record = records.get(4);
		Assert.assertEquals("soc", record.key());
		Assert.assertTrue(record.value().contains("99"));
		
		record = records.get(0);
		Assert.assertEquals("ic", record.key());
		Assert.assertTrue(record.value().contains("3000"));
		
		record = records.get(3);
		Assert.assertEquals("lv", record.key());
		Assert.assertTrue(record.value().contains("5000"));
		
		record = records.get(2);
		Assert.assertEquals("strv", record.key());
		
			
	}
	
	
	@Test
	public void testRecordsModule2() throws Exception{
		
		this.json = "{\n" + 
				"    \"deviceId\": \"ea57a230-0523-11ee-9b6b-ada73bb29091\",\n" + 
				
				"    \"ts\": \"1660657577000\",\n" + 
				
				"    \"BT1\": 44\n" +
				
				"}";
		
		ObjectMapper mapper = new ObjectMapper();
		Message message = mapper.readValue(json,Message.class);
		ProducerRecordCreator creator = new ProducerRecordCreator(message);
		creator.process();
		Assert.assertEquals(1, creator.getRecords().size());
		List<ProducerRecord<String, String>> records = creator.getRecords();
		
		ProducerRecord< String, String> record = records.get(0);
		Assert.assertEquals("tmp", record.key());
		Assert.assertTrue(record.value().contains("44"));
		
			
	}
	
	
	@Test
	public void testGetModulePosition() throws Exception{
		Method method = ProducerRecordCreator.class.getDeclaredMethod("getModulePosition", String.class);
		method.setAccessible(true);
		ProducerRecordCreator creator = new ProducerRecordCreator(null);
		Integer c = (Integer) method.invoke(creator, "B2SOC");
		Assert.assertEquals("2", c+"");
		
		c = (Integer) method.invoke(creator, "B22SOC");
		Assert.assertEquals("22", c+"");
		
		c = (Integer) method.invoke(creator, "B999SOC");
		Assert.assertEquals("999", c+"");
		
		
		
	}
	
	
	@Test
	public void testGetKeyIC() throws Exception{
		 
		ProducerRecordCreator producerRecordCreator = new ProducerRecordCreator(null);
		Method method = ProducerRecordCreator.class.getDeclaredMethod("getKafkaKey", String.class);
		method.setAccessible(true);
		String kafkaKey = (String) method.invoke(producerRecordCreator, "B0I");
		Assert.assertEquals("ic", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "Ip");
		Assert.assertEquals("ic", kafkaKey);
		
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "B1I");
		Assert.assertEquals("ic", kafkaKey);
		
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "B2I");
		Assert.assertEquals("ic", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "C");
		Assert.assertNull(kafkaKey);
		
		
	}
	
	
	@Test
	public void testGetKeyLv() throws Exception{
		 
		ProducerRecordCreator producerRecordCreator = new ProducerRecordCreator(null);
		Method method = ProducerRecordCreator.class.getDeclaredMethod("getKafkaKey", String.class);
		method.setAccessible(true);
		String kafkaKey = (String) method.invoke(producerRecordCreator, "B0Vp");
		Assert.assertEquals("lv", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "Vp");
		Assert.assertEquals("lv", kafkaKey);
		
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "B1Vp");
		Assert.assertEquals("lv", kafkaKey);
		
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "B2Vp");
		Assert.assertEquals("lv", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "v");
		Assert.assertNull(kafkaKey);
		
		
	}
	
	
	@Test
	public void testGetKeySOC() throws Exception{
		 
		ProducerRecordCreator producerRecordCreator = new ProducerRecordCreator(null);
		Method method = ProducerRecordCreator.class.getDeclaredMethod("getKafkaKey", String.class);
		method.setAccessible(true);
		String kafkaKey = (String) method.invoke(producerRecordCreator, "B0SOC");
		Assert.assertEquals("soc", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "SoC");
		Assert.assertEquals("soc", kafkaKey);
		
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "B1SOC");
		Assert.assertEquals("soc", kafkaKey);
		
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "B2SOC");
		Assert.assertEquals("soc", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "soc");
		Assert.assertNull(kafkaKey);
		
		
	}
	
	@Test
	public void testGetKeyTMP() throws Exception{
		 
		ProducerRecordCreator producerRecordCreator = new ProducerRecordCreator(null);
		Method method = ProducerRecordCreator.class.getDeclaredMethod("getKafkaKey", String.class);
		method.setAccessible(true);
		String kafkaKey = (String) method.invoke(producerRecordCreator, "B0T1");
		Assert.assertEquals("tmp", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "B1T1");
		Assert.assertEquals("tmp", kafkaKey);
		
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "B2T1");
		Assert.assertEquals("tmp", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "T1");
		Assert.assertNotNull(kafkaKey);
		
		
	}
	
	@Test
	public void testGetKeySTRV() throws Exception{
		 
		ProducerRecordCreator producerRecordCreator = new ProducerRecordCreator(null);
		Method method = ProducerRecordCreator.class.getDeclaredMethod("getKafkaKey", String.class);
		method.setAccessible(true);
		String kafkaKey = (String) method.invoke(producerRecordCreator, "B0V1");
		Assert.assertEquals("strv", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "B0V10");
		Assert.assertEquals("strv", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "B0V100");
		Assert.assertEquals("strv", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "B99V1");
		Assert.assertEquals("strv", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "B99V999");
		Assert.assertEquals("strv", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "V999");
		Assert.assertEquals("strv", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "V99");
		Assert.assertEquals("strv", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "V9");
		Assert.assertEquals("strv", kafkaKey);
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "V1");
		Assert.assertEquals("strv", kafkaKey);
		
		
		
		kafkaKey = (String) method.invoke(producerRecordCreator, "STRV");
		Assert.assertNull(kafkaKey);
		
		
	} 
	
	@Test
	public void testPutKeys() throws Exception{
		 
		Map<String, List<String>> keyMapT = new HashMap<>();
		Method method = ProducerRecordCreator.class.getDeclaredMethod("putKeys", String.class, String.class, Map.class);
		method.setAccessible(true);
		ProducerRecordCreator creator = new ProducerRecordCreator(null);
		for(KeyMap key : this.keyMaps) {
			method.invoke(creator, "ic", key.getIc(), keyMapT);
		}
		Assert.assertEquals("{ic=[B{p}I, B{p}I, B{p}I, Ip]}", keyMapT.toString());
				
		
	}
	
	@Test
	public void testGetStringIndexFromKey() throws Exception{
		ProducerRecordCreator producerRecordCreator = new ProducerRecordCreator(null);
		Method method = ProducerRecordCreator.class.getDeclaredMethod("getStringIndexFromKey", String.class);
		method.setAccessible(true);
		Integer index = (Integer)method.invoke(producerRecordCreator, "B0V1");
		Assert.assertEquals("1", index+"");
		
		index = (Integer)method.invoke(producerRecordCreator, "V1");
		Assert.assertEquals("1", index+"");
		
		index = (Integer)method.invoke(producerRecordCreator, "B0V11");
		Assert.assertEquals("11", index+"");
		
		index = (Integer)method.invoke(producerRecordCreator, "B99V99");
		Assert.assertEquals("99", index+"");
		
		index = (Integer)method.invoke(producerRecordCreator, "B3V876");
		Assert.assertEquals("876", index+"");
	}
	
	@Test
	public void testGetPositionFromModule() throws Exception {
		Method method = ProducerRecordCreator.class.getDeclaredMethod("getPositionFromModule", String.class);
		method.setAccessible(true);
		ProducerRecordCreator creator = new ProducerRecordCreator(null);
		Integer c = (Integer) method.invoke(creator, "f92b3400-3298-11ee-980a-31ea805986ca");
		Assert.assertEquals("2", c+"");
	}
	
	
	
}

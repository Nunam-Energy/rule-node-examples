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
import java.lang.reflect.Method;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProducerRecordCreatorTest {

	private static String json;
	
	@Before
	public void set() {
		this.json = "{\n" + 
				"    \"deviceId\": \"688bd700-1d69-11ed-9367-87c11a2a900d\",\n" + 
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
		Assert.assertEquals(7, creator.getRecords().size());
		List<ProducerRecord<String, String>> records = creator.getRecords();
		
		ProducerRecord< String, String> record = records.get(6);
		Assert.assertEquals("discharge_capacity", record.key());
		Assert.assertTrue(record.value().contains("444"));
		
		record = records.get(1);
		Assert.assertEquals("tmp_1", record.key());
		Assert.assertTrue(record.value().contains("32"));
		
		record = records.get(2);
		Assert.assertEquals("tmp_2", record.key());
		Assert.assertTrue(record.value().contains("33"));
		
		
		record = records.get(5);
		Assert.assertEquals("soc", record.key());
		Assert.assertTrue(record.value().contains("99"));
		
		record = records.get(0);
		Assert.assertEquals("ic", record.key());
		Assert.assertTrue(record.value().contains("3000"));
		
		record = records.get(4);
		Assert.assertEquals("lv", record.key());
		Assert.assertTrue(record.value().contains("5000"));
		
		record = records.get(3);
		Assert.assertEquals("strv", record.key());
		
			
	}
	
	@Test
	public void testRecordsModule1() throws Exception{
		
		this.json = "{\n" + 
				"    \"deviceId\": \"688bd700-1d69-11ed-9367-87c11a2a900d\",\n" + 
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
		Assert.assertEquals(7, creator.getRecords().size());
		List<ProducerRecord<String, String>> records = creator.getRecords();
		
		ProducerRecord< String, String> record = records.get(6);
		Assert.assertEquals("discharge_capacity", record.key());
		Assert.assertTrue(record.value().contains("444"));
		
		record = records.get(1);
		Assert.assertEquals("tmp_1", record.key());
		Assert.assertTrue(record.value().contains("32"));
		
		record = records.get(2);
		Assert.assertEquals("tmp_2", record.key());
		Assert.assertTrue(record.value().contains("33"));
		
		
		record = records.get(5);
		Assert.assertEquals("soc", record.key());
		Assert.assertTrue(record.value().contains("99"));
		
		record = records.get(0);
		Assert.assertEquals("ic", record.key());
		Assert.assertTrue(record.value().contains("3000"));
		
		record = records.get(4);
		Assert.assertEquals("lv", record.key());
		Assert.assertTrue(record.value().contains("5000"));
		
		record = records.get(3);
		Assert.assertEquals("strv", record.key());
		
			
	}
	
	
	
	@Test
	public void testGetGenericKeyName() throws Exception{
		Method method = ProducerRecordCreator.class.getDeclaredMethod("getGenericKeyName", String.class);
		method.setAccessible(true);
		ProducerRecordCreator creator = new ProducerRecordCreator(null);
		String name = (String)method.invoke(creator, "B0V1");
		Assert.assertEquals("B*V1", name);
		
		name = (String)method.invoke(creator, "B0T1");
		Assert.assertEquals("B*T1", name);
		
		name = (String)method.invoke(creator, "B0SOC");
		Assert.assertEquals("B*SOC", name);
		
	}
	
	@Test
	public void testGetModulePosition() throws Exception{
		Method method = ProducerRecordCreator.class.getDeclaredMethod("getModulePosition", String.class);
		method.setAccessible(true);
		ProducerRecordCreator creator = new ProducerRecordCreator(null);
		char c = (char) method.invoke(creator, "B2SOC");
		Assert.assertEquals('2', c);
		
		c = (char) method.invoke(creator, "B2I");
		Assert.assertEquals('2', c);
		
		c = (char) method.invoke(creator, "B0Vp");
		Assert.assertEquals('0', c);
		
	}
	
	
	
}

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
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TempStringVoltagesTests {

	@Test
	public void testIsFull() throws Exception{
		TempStringVoltages stringVoltages = TempStringVoltages.getTempStringVoltages();
		Field field = TempStringVoltages.class.getDeclaredField("tempMessages");
		field.setAccessible(true);
		Map<String, KafkaStringVoltageMessage> map = new HashMap<>();
		KafkaStringVoltageMessage kafkaStringVoltageMessage = new KafkaStringVoltageMessage();
		kafkaStringVoltageMessage.setTs(1001l);
		StringVoltage sVoltages = new StringVoltage();
		sVoltages.setStrv1("1001");
		sVoltages.setStrv2("1001");
		sVoltages.setStrv3("1001");
		sVoltages.setStrv4("1001");
		sVoltages.setStrv5("1001");
		sVoltages.setStrv6("1001");
		sVoltages.setStrv7("1001");
		sVoltages.setStrv8("1001");
		sVoltages.setStrv9("1001");
		sVoltages.setStrv10("1001");
		sVoltages.setStrv11("1001");
		sVoltages.setStrv12("1001");
		sVoltages.setStrv13("1001");
		sVoltages.setStrv14("1001");
		sVoltages.setStrv15("1001");
		sVoltages.setStrv16("1001");
		sVoltages.setStrv17("1001");
		sVoltages.setStrv18("1001");
		sVoltages.setStrv19("1001");
		sVoltages.setStrv20("1001");
		kafkaStringVoltageMessage.setValue(sVoltages);
		Method method = TempStringVoltages.class.getDeclaredMethod("isMessageFull", KafkaStringVoltageMessage.class);
		method.setAccessible(true);
		Boolean result = (Boolean) method.invoke(stringVoltages, kafkaStringVoltageMessage);
		Assert.assertTrue(result);
		
				
	}
	
	@Test
	public void testIsFullForFalse() throws Exception{
		TempStringVoltages stringVoltages = TempStringVoltages.getTempStringVoltages();
		Field field = TempStringVoltages.class.getDeclaredField("tempMessages");
		field.setAccessible(true);
		Map<String, KafkaStringVoltageMessage> map = new HashMap<>();
		KafkaStringVoltageMessage kafkaStringVoltageMessage = new KafkaStringVoltageMessage();
		kafkaStringVoltageMessage.setTs(1001l);
		StringVoltage sVoltages = new StringVoltage();
		sVoltages.setStrv1("1001");
		sVoltages.setStrv2("1001");
		sVoltages.setStrv3("1001");
		sVoltages.setStrv4("1001");
		sVoltages.setStrv5("1001");
		sVoltages.setStrv6("1001");
		sVoltages.setStrv7("1001");
		sVoltages.setStrv8("1001");
		sVoltages.setStrv9("1001");
		sVoltages.setStrv10("1001");
		sVoltages.setStrv11("1001");
		sVoltages.setStrv12("1001");
		sVoltages.setStrv14("1001");
		sVoltages.setStrv15("1001");
		sVoltages.setStrv16("1001");
		sVoltages.setStrv17("1001");
		sVoltages.setStrv18("1001");
		sVoltages.setStrv19("1001");
		sVoltages.setStrv20("1001");
		kafkaStringVoltageMessage.setValue(sVoltages);
		Method method = TempStringVoltages.class.getDeclaredMethod("isMessageFull", KafkaStringVoltageMessage.class);
		method.setAccessible(true);
		Boolean result = (Boolean) method.invoke(stringVoltages, kafkaStringVoltageMessage);
		Assert.assertFalse(result);		
	}
	
	@Test
	public void testSetVoltage() throws Exception{
		TempStringVoltages stringVoltages = TempStringVoltages.getTempStringVoltages();
		Field field = TempStringVoltages.class.getDeclaredField("tempMessages");
		field.setAccessible(true);
		Map<String, KafkaStringVoltageMessage> map = new HashMap<>();
		KafkaStringVoltageMessage kafkaStringVoltageMessage = new KafkaStringVoltageMessage();
		Method method = TempStringVoltages.class.getDeclaredMethod("setVoltage", new Class[] {KafkaStringVoltageMessage.class, Integer.class,  String.class});
		method.setAccessible(true);
		method.invoke(stringVoltages, kafkaStringVoltageMessage, 1, "1001");
		Assert.assertEquals(kafkaStringVoltageMessage.getValue().getStrv1(), "1001");
	}
	
	
	@Test
	public void testSetVoltageStrv12() throws Exception{
		TempStringVoltages stringVoltages = TempStringVoltages.getTempStringVoltages();
		Field field = TempStringVoltages.class.getDeclaredField("tempMessages");
		field.setAccessible(true);
		Map<String, KafkaStringVoltageMessage> map = new HashMap<>();
		KafkaStringVoltageMessage kafkaStringVoltageMessage = new KafkaStringVoltageMessage();
		Method method = TempStringVoltages.class.getDeclaredMethod("setVoltage", new Class[] {KafkaStringVoltageMessage.class, Integer.class,  String.class});
		method.setAccessible(true);
		method.invoke(stringVoltages, kafkaStringVoltageMessage, 12, "1012");
		Assert.assertEquals(kafkaStringVoltageMessage.getValue().getStrv12(), "1012");
	}
}

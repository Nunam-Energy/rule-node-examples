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

import static org.assertj.core.api.Assertions.setLenientDateParsing;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TempTemperatureSeriesTests {

	@Test
	public void testSetTemperature() throws Exception {
		TempTemperatireSeries tempTemperatireSeries = TempTemperatireSeries.getTempTemperatireSeries();
		Field field = TempStringVoltages.class.getDeclaredField("tempMessages");
		field.setAccessible(true);
		Map<String, KafkaStringVoltageMessage> map = new HashMap<>();
		KafkaTemperatureSeriesMessage kafkaTemperatureSeriesMessage = new KafkaTemperatureSeriesMessage();
		Method method = TempTemperatireSeries.class.getDeclaredMethod("setTemperature",
				new Class[] { KafkaTemperatureSeriesMessage.class, Integer.class, String.class });
		method.setAccessible(true);
		method.invoke(tempTemperatireSeries, kafkaTemperatureSeriesMessage, 1, "1001");
		Assert.assertEquals(kafkaTemperatureSeriesMessage.getValue().getTmp1(), "1001");
	}

	@Test
	public void testSetTemperature10() throws Exception {
		TempTemperatireSeries tempTemperatireSeries = TempTemperatireSeries.getTempTemperatireSeries();
		Field field = TempStringVoltages.class.getDeclaredField("tempMessages");
		field.setAccessible(true);
		Map<String, KafkaStringVoltageMessage> map = new HashMap<>();
		KafkaTemperatureSeriesMessage kafkaTemperatureSeriesMessage = new KafkaTemperatureSeriesMessage();
		Method method = TempTemperatireSeries.class.getDeclaredMethod("setTemperature",
				new Class[] { KafkaTemperatureSeriesMessage.class, Integer.class, String.class });
		method.setAccessible(true);
		method.invoke(tempTemperatireSeries, kafkaTemperatureSeriesMessage, 10, "1001");
		Assert.assertEquals(kafkaTemperatureSeriesMessage.getValue().getTmp10(), "1001");
	}

	@Test
	public void testIsFull() throws Exception {
		TempTemperatireSeries tempTemperatireSeries = TempTemperatireSeries.getTempTemperatireSeries();
		Field field = TempStringVoltages.class.getDeclaredField("tempMessages");
		field.setAccessible(true);
		Map<String, KafkaTemperatureSeriesMessage> map = new HashMap<>();
		KafkaTemperatureSeriesMessage kafkaTemperatureSeriesMessage = new KafkaTemperatureSeriesMessage();
		kafkaTemperatureSeriesMessage.setTs(1001l);
		TemperatureSeries temperatureSeries = new TemperatureSeries();
		temperatureSeries.setTmp1("32");
		temperatureSeries.setTmp2("32");
		temperatureSeries.setTmp3("32");
		temperatureSeries.setTmp4("32");
		temperatureSeries.setTmp5("32");
		temperatureSeries.setTmp6("32");
		temperatureSeries.setTmp7("32");
		temperatureSeries.setTmp8("32");
		temperatureSeries.setTmp9("32");
		temperatureSeries.setTmp10("32");
		kafkaTemperatureSeriesMessage.setValue(temperatureSeries);
		Method method = TempTemperatireSeries.class.getDeclaredMethod("isMessageFull", new Class[] {KafkaTemperatureSeriesMessage.class});
		method.setAccessible(true);
		Boolean result = (Boolean) method.invoke(tempTemperatireSeries, kafkaTemperatureSeriesMessage);
		Assert.assertTrue(result);

	}

	@Test
	public void testIsFullForFalse() throws Exception{
		TempTemperatireSeries tempTemperatireSeries = TempTemperatireSeries.getTempTemperatireSeries(); 
		Field field = TempStringVoltages.class.getDeclaredField("tempMessages");
		field.setAccessible(true);
		Map<String, KafkaTemperatureSeriesMessage> map = new HashMap<>();
		TemperatureSeries temperatureSeries = new TemperatureSeries();
		temperatureSeries.setTmp1("32");
		KafkaTemperatureSeriesMessage kafkaTemperatureSeriesMessage = new KafkaTemperatureSeriesMessage();
		kafkaTemperatureSeriesMessage.setTs(1001l);
		kafkaTemperatureSeriesMessage.setValue(temperatureSeries);
		Method method = TempTemperatireSeries.class.getDeclaredMethod("isMessageFull", KafkaTemperatureSeriesMessage.class);
		method.setAccessible(true);
		Boolean result = (Boolean) method.invoke(tempTemperatireSeries, kafkaTemperatureSeriesMessage);
		Assert.assertFalse(result);		
	}
	
	
	@Test
	public void testCheckForTemperatureForMessageCompletion() throws Exception{
		//String deviceId, Long ts, Integer sensorPosition, String value
		TempTemperatireSeries tempTemperatireSeries = TempTemperatireSeries.getTempTemperatireSeries();
		KafkaTemperatureSeriesMessage msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 1, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 2, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 3, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 4, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 5, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 6, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 7, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 8, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 9, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 10, "30");
		Assert.assertNotNull(msg);
		Assert.assertEquals("30", msg.getValue().getTmp10());
		
		
	}
	
	@Test
	public void testCheckForTemperatureForDeviceChange() throws Exception{
		//String deviceId, Long ts, Integer sensorPosition, String value
		TempTemperatireSeries tempTemperatireSeries = TempTemperatireSeries.getTempTemperatireSeries();
		KafkaTemperatureSeriesMessage msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 1, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 2, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 3, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12345l, 4, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12346l, 5, "32");
		Assert.assertNotNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12346l, 6, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12346l, 7, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12346l, 8, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12346l, 9, "32");
		Assert.assertNull(msg);
		msg = tempTemperatireSeries.check("08f373e0-a459-11ec-b31b-a78bfb439bd1", 12346l, 10, "30");
		Assert.assertNull(msg);
		
		
		
	}

}

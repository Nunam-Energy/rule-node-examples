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
import java.util.HashMap;
import java.util.Map;

public class TempTemperatireSeries {

	private static TempTemperatireSeries tempTemperatireSeries;

	private static Map<String, KafkaTemperatureSeriesMessage> tempMessages = new HashMap<>();

	private TempTemperatireSeries() {
		// TODO Auto-generated constructor stub
	}

	public static TempTemperatireSeries getTempTemperatireSeries() {
		if (tempTemperatireSeries == null) {
			tempTemperatireSeries = new TempTemperatireSeries();
		}
		return tempTemperatireSeries;
	}

	public KafkaTemperatureSeriesMessage check(String deviceId, Long ts, Integer sensorPosition, String value)
			throws Exception {
		KafkaTemperatureSeriesMessage messages = this.tempMessages.get(deviceId);
		if (messages != null) {
			if (messages.getTs().equals(ts)) {
				this.setTemperature(messages, sensorPosition, value);
				if (this.isMessageFull(messages)) {
					TempTemperatireSeries.tempMessages.remove(deviceId);
					return messages;
				} else {
					this.tempMessages.put(deviceId, messages);
					return null;
				}
			} else {
				KafkaTemperatureSeriesMessage oldMessage = TempTemperatireSeries.tempMessages.get(deviceId);
				KafkaTemperatureSeriesMessage newMessages = new KafkaTemperatureSeriesMessage();
				newMessages.setTs(ts);
				this.setTemperature(newMessages, sensorPosition, value);
				TempTemperatireSeries.tempMessages.put(deviceId, newMessages);
				return oldMessage;
			}
		} else {
			KafkaTemperatureSeriesMessage newMessages = new KafkaTemperatureSeriesMessage();
			newMessages.setTs(ts);
			this.setTemperature(newMessages, sensorPosition, value);
			TempTemperatireSeries.tempMessages.put(deviceId, newMessages);
			return null;
		}

	}

	private void setTemperature(KafkaTemperatureSeriesMessage messages, Integer sensorPosition, String value) throws Exception {
		Field field = TemperatureSeries.class.getDeclaredField("tmp" + sensorPosition);
		field.setAccessible(true);
		field.set(messages.getValue(), value);
	}

	private boolean isMessageFull(KafkaTemperatureSeriesMessage message)
			throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = TemperatureSeries.class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.get(message.getValue()) == null) {
				return false;
			}
		}
		return true;
	}
}

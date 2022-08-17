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

public class TempStringVoltages {

	private static TempStringVoltages tempStringVoltages;
	
	private static Map<String, KafkaStringVoltageMessage> tempMessages = new HashMap<>();
	
	private TempStringVoltages() {
		// TODO Auto-generated constructor stub
	}
	
	public static TempStringVoltages getTempStringVoltages() {
		if(tempStringVoltages==null) {
			tempStringVoltages = new TempStringVoltages();
		}
		return tempStringVoltages;
	}
	
	public KafkaStringVoltageMessage check(String deviceId, Long ts, Integer stringPosition, String value) throws Exception {
		KafkaStringVoltageMessage messages = this.tempMessages.get(deviceId);
		if(messages!=null) {
			if(messages.getTs().equals(ts)) {
				this.setVoltage(messages, stringPosition, value);
				if(this.isMessageFull(messages)) {
					TempStringVoltages.tempMessages.remove(deviceId);
					return messages;
				}else {
					this.tempMessages.put(deviceId, messages);
					return null;
				}
			}else {
				KafkaStringVoltageMessage oldMessage = TempStringVoltages.tempMessages.get(deviceId); 
				KafkaStringVoltageMessage newMessages = new KafkaStringVoltageMessage();
				newMessages.setTs(ts);
				this.setVoltage(newMessages, stringPosition, value);
				TempStringVoltages.tempMessages.put(deviceId, newMessages);
				return oldMessage;
			}
		}else {
			KafkaStringVoltageMessage newMessages = new KafkaStringVoltageMessage();
			newMessages.setTs(ts);
			this.setVoltage(newMessages, stringPosition, value);
			TempStringVoltages.tempMessages.put(deviceId, newMessages);
			return null;
		}
		
	} 
	
	private void setVoltage(KafkaStringVoltageMessage messages, Integer stringPosition, String value) throws Exception {
		Field field = StringVoltage.class.getDeclaredField("strv"+stringPosition);
		field.setAccessible(true);
		field.set(messages.getValue(), value);
	}
	
	private boolean isMessageFull(KafkaStringVoltageMessage message) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = StringVoltage.class.getDeclaredFields();
		for(Field field:fields) {
			field.setAccessible(true);
			if(field.get(message.getValue())==null) {
				return false;
			}
		}
		return true;
	}
	
}

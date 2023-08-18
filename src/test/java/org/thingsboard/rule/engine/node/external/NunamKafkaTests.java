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

import static org.mockito.Mockito.doNothing;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.kafka.clients.consumer.ConsumerGroupMetadata;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.thingsboard.rule.engine.api.TbContext;
import org.thingsboard.server.common.msg.TbMsg;
import org.thingsboard.server.common.msg.TbMsgMetaData;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;


public class NunamKafkaTests {

	private static String assertHelper;
	Producer<String, String> producer;
	TbMsg tbMsg;
	ObjectMapper mapper;
	
	@Before
	public void before() {
		mapper = new ObjectMapper();
	}
	
	public void set(Function<ProducerRecord<String, String>, ProducerRecord<String, String>> fun, NunamKafka kafkaNode) throws Exception {
		Field field = NunamKafka.class.getDeclaredField("producer");
		field.setAccessible(true);
		Producer producer = new Producer<String, String>() {

			@Override
			public void initTransactions() {
				
			}

			@Override
			public void beginTransaction() throws ProducerFencedException {
			}

			@Override
			public void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets, String consumerGroupId)
					throws ProducerFencedException {
			}

			@Override
			public void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets,
					ConsumerGroupMetadata groupMetadata) throws ProducerFencedException {
			}

			@Override
			public void commitTransaction() throws ProducerFencedException {
			}

			@Override
			public void abortTransaction() throws ProducerFencedException {
			}

			@Override
			public Future<RecordMetadata> send(ProducerRecord<String, String> record) {
				
				fun.apply(record);
				return null;
			}

			@Override
			public Future<RecordMetadata> send(ProducerRecord<String, String> record, Callback callback) {
				return null;
			}

			@Override
			public void flush() {
				
			}

			@Override
			public List<PartitionInfo> partitionsFor(String topic) {
				return null;
			}

			@Override
			public Map<MetricName, ? extends Metric> metrics() {
				return null;
			}

			@Override
			public void close() {
				
			}

			@Override
			public void close(Duration timeout) {
				
			}
		};
		field.set(kafkaNode, producer);
		
	}
	
	@Test
	public void testB0Q() throws Exception {
		 
		Function<ProducerRecord<String, String>, ProducerRecord<String, String>> asserter = new Function<ProducerRecord<String, String>, ProducerRecord<String, String>>() {

			@Override
			public @Nullable ProducerRecord<String, String> apply(@Nullable ProducerRecord<String, String> input) {
				try {
					String key = input.key();
					String value = input.value();
					
					KafkaMessage message = mapper.readValue(value, KafkaMessage.class);
					Assert.assertEquals("capacity_discharge",key);
					Assert.assertEquals("444",message.getValue());
				}catch (Exception e) {
					// TODO: handle exception
				}
				return null;
			}
		};
		NunamKafka kafka = new NunamKafka();
		set(asserter, kafka);
		String json = "{\n" + 
				"    \"deviceId\": \"140cd230-0e3a-11ed-9bda-0163f5a9393b\",\n" + 
				"    \"ts\": \"1660657577000\",\n" + 
				"    \"B0Q\": 444\n" +
				"}";
		
		Map<String, String> data = new HashMap<>();
		TbMsgMetaData dt = new TbMsgMetaData(data);
		TbMsg tbMsg = TbMsg.newMsg(null, null, null, dt, json, null, null);
		
		TbContext context = Mockito.mock(TbContext.class);
		doNothing().when(context).tellSuccess(tbMsg);
		kafka.onMsg(context, tbMsg);
		
		
	}
	
	@Test
	public void testIC() throws Exception {
		 
		Function<ProducerRecord<String, String>, ProducerRecord<String, String>> asserter = new Function<ProducerRecord<String, String>, ProducerRecord<String, String>>() {

			@Override
			public @Nullable ProducerRecord<String, String> apply(@Nullable ProducerRecord<String, String> input) {
				try {
					String key = input.key();
					String value = input.value();
					
					KafkaMessage message = mapper.readValue(value, KafkaMessage.class);
					Assert.assertEquals("ic",key);
					Assert.assertEquals("444",message.getValue());
				}catch (Exception e) {
					// TODO: handle exception
				}
				return null;
			}
		};
		NunamKafka kafka = new NunamKafka();
		set(asserter, kafka);
		String json = "{\n" + 
				"    \"deviceId\": \"140cd230-0e3a-11ed-9bda-0163f5a9393b\",\n" + 
				"    \"ts\": \"1660657577000\",\n" + 
				"    \"B0I\": 444\n" +
				"}";
		
		Map<String, String> data = new HashMap<>();
		TbMsgMetaData dt = new TbMsgMetaData(data);
		TbMsg tbMsg = TbMsg.newMsg(null, null, null, dt, json, null, null);
		
		TbContext context = Mockito.mock(TbContext.class);
		doNothing().when(context).tellSuccess(tbMsg);
		kafka.onMsg(context, tbMsg);
		
		
	}
	
	@Test
	public void testStrVIncomplete() throws Exception {
		 
		Function<ProducerRecord<String, String>, ProducerRecord<String, String>> asserter = new Function<ProducerRecord<String, String>, ProducerRecord<String, String>>() {

			@Override
			public @Nullable ProducerRecord<String, String> apply(@Nullable ProducerRecord<String, String> input) {
				try {
					Assert.fail();
				}catch (Exception e) {
					// TODO: handle exception
				}
				return null;
			}
		};
		NunamKafka kafka = new NunamKafka();
		set(asserter, kafka);
		String json = "{\n" + 
				"    \"deviceId\": \"140cd230-0e3a-11ed-9bda-0163f5a9393b\",\n" + 
				"    \"ts\": \"1660657577000\",\n" + 
				"    \"B0V1\": 1001,\n" +
				"    \"B0V2\": 1002,\n" +
				"    \"B0V3\": 1003,\n" +
				"    \"B0V4\": 1005,\n" +
				"    \"B0V5\": 1005,\n" +
				"    \"B0V6\": 1006\n" +
				"}";
		
		Map<String, String> data = new HashMap<>();
		TbMsgMetaData dt = new TbMsgMetaData(data);
		TbMsg tbMsg = TbMsg.newMsg(null, null, null, dt, json, null, null);
		
		TbContext context = Mockito.mock(TbContext.class);
		doNothing().when(context).tellSuccess(tbMsg);
		kafka.onMsg(context, tbMsg);
		Assert.assertTrue(true);
		
		
	}
	
	@Test
	public void testStrVComplete() throws Exception {
		
		 
		Function<ProducerRecord<String, String>, ProducerRecord<String, String>> asserter = new Function<ProducerRecord<String, String>, ProducerRecord<String, String>>() {

			@Override
			public @Nullable ProducerRecord<String, String> apply(@Nullable ProducerRecord<String, String> input) {
				try {
					String key = input.key();
					String value = input.value();
					NunamKafkaTests.assertHelper = "called";
					KafkaStringVoltageMessage message = mapper.readValue(value, KafkaStringVoltageMessage.class);
					Assert.assertEquals("strv",key);
					Assert.assertEquals("1001",message.getValue().getStrv1());
					Assert.assertEquals("1002",message.getValue().getStrv2());
					Assert.assertEquals("1003",message.getValue().getStrv3());
					Assert.assertEquals("1004",message.getValue().getStrv4());
					Assert.assertEquals("1005",message.getValue().getStrv5());
					Assert.assertEquals("1006",message.getValue().getStrv6());
					Assert.assertEquals("1007",message.getValue().getStrv7());
					Assert.assertEquals("1008",message.getValue().getStrv8());
					Assert.assertEquals("1009",message.getValue().getStrv9());
					Assert.assertEquals("1010",message.getValue().getStrv10());
					Assert.assertEquals("1011",message.getValue().getStrv11());
					Assert.assertEquals("1012",message.getValue().getStrv12());
					Assert.assertEquals("1013",message.getValue().getStrv13());
					Assert.assertEquals("1014",message.getValue().getStrv14());
					Assert.assertEquals("1015",message.getValue().getStrv15());
					Assert.assertEquals("1016",message.getValue().getStrv16());
					Assert.assertEquals("1017",message.getValue().getStrv17());
					Assert.assertEquals("1018",message.getValue().getStrv18());
					Assert.assertEquals("1019",message.getValue().getStrv19());
					Assert.assertEquals("1020",message.getValue().getStrv20());
				}catch (Exception e) {
					// TODO: handle exception
				}
				return null;
			}
		};
		NunamKafkaTests.assertHelper = null;
		NunamKafka kafka = new NunamKafka();
		set(asserter, kafka);
		String json = "{\n" + 
				"    \"deviceId\": \"140cd230-0e3a-11ed-9bda-0163f5a9393b\",\n" + 
				"    \"ts\": \"1660657577000\",\n" + 
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
		
		Map<String, String> data = new HashMap<>();
		TbMsgMetaData dt = new TbMsgMetaData(data);
		TbMsg tbMsg = TbMsg.newMsg(null, null, null, dt, json, null, null);
		
		TbContext context = Mockito.mock(TbContext.class);
		doNothing().when(context).tellSuccess(tbMsg);
		kafka.onMsg(context, tbMsg);
		Assert.assertEquals("called", NunamKafkaTests.assertHelper);
	}
	
	@Test
	public void testStrVCompleteByPart() throws Exception {
		 
		Function<ProducerRecord<String, String>, ProducerRecord<String, String>> asserter = new Function<ProducerRecord<String, String>, ProducerRecord<String, String>>() {

			@Override
			public @Nullable ProducerRecord<String, String> apply(@Nullable ProducerRecord<String, String> input) {
				try {
					NunamKafkaTests.assertHelper = "called";
					String key = input.key();
					String value = input.value();
					
					KafkaStringVoltageMessage message = mapper.readValue(value, KafkaStringVoltageMessage.class);
					Assert.assertEquals("strv",key);
					Assert.assertEquals("1016",message.getValue().getStrv16());
				}catch (Exception e) {
					// TODO: handle exception
				}
				return null;
			}
		};
		NunamKafkaTests.assertHelper = null;
		NunamKafka kafka = new NunamKafka();
		set(asserter, kafka);
		String json = "{\n" + 
				"    \"deviceId\": \"140cd230-0e3a-11ed-9bda-0163f5a9393b\",\n" + 
				"    \"ts\": \"1660657577000\",\n" + 
				"    \"B0V1\": 1001,\n" +
				"    \"B0V2\": 1002,\n" +
				"    \"B0V3\": 1003,\n" +
				"    \"B0V4\": 1004,\n" +
				"    \"B0V5\": 1005,\n" +
				"    \"B0V6\": 1006\n" +
				"}";
		Map<String, String> data = new HashMap<>();
		TbMsgMetaData dt = new TbMsgMetaData(data);
		TbMsg tbMsg = TbMsg.newMsg(null, null, null, dt, json, null, null);
		
		TbContext context = Mockito.mock(TbContext.class);
		doNothing().when(context).tellSuccess(tbMsg);
		
		kafka.onMsg(context, tbMsg);
		Assert.assertNull(NunamKafkaTests.assertHelper);

		json = "{\n" + 
				"    \"deviceId\": \"140cd230-0e3a-11ed-9bda-0163f5a9393b\",\n" + 
				"    \"ts\": \"1660657577000\",\n" + 
				"    \"B0V7\": 1007,\n" +
				"    \"B0V8\": 1008,\n" +
				"    \"B0V9\": 1009,\n" +
				"    \"B0V10\": 1010,\n" +
				"    \"B0V11\": 1011,\n" +
				"    \"B0V12\": 1012\n" +
				"}";
		tbMsg = TbMsg.newMsg(null, null, null, dt, json, null, null);
		kafka.onMsg(context, tbMsg);
		Assert.assertNull(NunamKafkaTests.assertHelper);
		
		json = "{\n" + 
				"    \"deviceId\": \"140cd230-0e3a-11ed-9bda-0163f5a9393b\",\n" + 
				"    \"ts\": \"1660657577000\",\n" + 
				"    \"B0V13\": 1013,\n" +
				"    \"B0V14\": 1014,\n" +
				"    \"B0V15\": 1015,\n" +
				"    \"B0V16\": 1016,\n" +
				"    \"B0V17\": 1017,\n" +
				"    \"B0V18\": 1018,\n" +
				"    \"B0V19\": 1019,\n" +
				"    \"B0V20\": 1020\n" +
				"}";
		tbMsg = TbMsg.newMsg(null, null, null, dt, json, null, null);
		kafka.onMsg(context, tbMsg);
		Assert.assertEquals("called", NunamKafkaTests.assertHelper);
		
	}
	
	@Test
	public void testStrVIncompleteMessageOnNewMessage() throws Exception {
		 
		Function<ProducerRecord<String, String>, ProducerRecord<String, String>> asserter = new Function<ProducerRecord<String, String>, ProducerRecord<String, String>>() {

			@Override
			public @Nullable ProducerRecord<String, String> apply(@Nullable ProducerRecord<String, String> input) {
				try {
					NunamKafkaTests.assertHelper = "called";
					String key = input.key();
					String value = input.value();
					
					KafkaStringVoltageMessage message = mapper.readValue(value, KafkaStringVoltageMessage.class);
					Assert.assertEquals("strv",key);
					Assert.assertEquals("1006",message.getValue().getStrv6());
					Assert.assertNull(message.getValue().getStrv7());
				}catch (Exception e) {
					// TODO: handle exception
				}
				return null;
			}
		};
		NunamKafkaTests.assertHelper = null;
		NunamKafka kafka = new NunamKafka();
		set(asserter, kafka);
		String json = "{\n" + 
				"    \"deviceId\": \"140cd230-0e3a-11ed-9bda-0163f5a9393b\",\n" + 
				"    \"ts\": \"1660657577000\",\n" + 
				"    \"B0V1\": 1001,\n" +
				"    \"B0V2\": 1002,\n" +
				"    \"B0V3\": 1003,\n" +
				"    \"B0V4\": 1004,\n" +
				"    \"B0V5\": 1005,\n" +
				"    \"B0V6\": 1006\n" +
				"}";
		Map<String, String> data = new HashMap<>();
		TbMsgMetaData dt = new TbMsgMetaData(data);
		TbMsg tbMsg = TbMsg.newMsg(null, null, null, dt, json, null, null);
		TbContext context = Mockito.mock(TbContext.class);
		doNothing().when(context).tellSuccess(tbMsg);
		kafka.onMsg(context, tbMsg);
		Assert.assertNull(NunamKafkaTests.assertHelper);
		
		
		json = "{\n" + 
				"    \"deviceId\": \"140cd230-0e3a-11ed-9bda-0163f5a9393b\",\n" + 
				"    \"ts\": \"1660657578000\",\n" + 
				"    \"B0V1\": 1001,\n" +
				"    \"B0V2\": 1002,\n" +
				"    \"B0V3\": 1003,\n" +
				"    \"B0V4\": 1004,\n" +
				"    \"B0V5\": 1005,\n" +
				"    \"B0V6\": 1006\n" +
				"}";
		tbMsg = TbMsg.newMsg(null, null, null, dt, json, null, null);
		kafka.onMsg(context, tbMsg);
		Assert.assertNotNull(NunamKafkaTests.assertHelper);
		NunamKafkaTests.assertHelper = null;
		asserter = new Function<ProducerRecord<String, String>, ProducerRecord<String, String>>() {

			@Override
			public @Nullable ProducerRecord<String, String> apply(@Nullable ProducerRecord<String, String> input) {
				try {
					NunamKafkaTests.assertHelper = "called";
					String key = input.key();
					String value = input.value();
					Assert.assertEquals("strv",key);
					KafkaStringVoltageMessage message = mapper.readValue(value, KafkaStringVoltageMessage.class);
					Assert.assertEquals((Long)1660657578000l,message.getTs());
					Assert.assertEquals("1007",message.getValue().getStrv7());
				}catch (Exception e) {
					// TODO: handle exception
				}
				return null;
			}
		};
		set(asserter, kafka);
		json = "{\n" + 
				"    \"deviceId\": \"140cd230-0e3a-11ed-9bda-0163f5a9393b\",\n" + 
				"    \"ts\": \"1660657578000\",\n" + 
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
		tbMsg = TbMsg.newMsg(null, null, null, dt, json, null, null);
		kafka.onMsg(context, tbMsg);
		Assert.assertNotNull(NunamKafkaTests.assertHelper);
		
		
	}
	
}

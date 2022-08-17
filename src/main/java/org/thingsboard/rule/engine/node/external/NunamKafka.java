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

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;
import org.thingsboard.rule.engine.api.RuleNode;
import org.thingsboard.rule.engine.api.TbContext;
import org.thingsboard.rule.engine.api.TbNode;
import org.thingsboard.rule.engine.api.TbNodeConfiguration;
import org.thingsboard.rule.engine.api.TbNodeException;
import org.thingsboard.server.common.data.plugin.ComponentType;
import org.thingsboard.server.common.msg.TbMsg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RuleNode(configClazz = NunamKafkaConfiguration.class, name = "Nunam Kafka", nodeDescription = "Kafka Integration Node", nodeDetails = "Kafka Integration Node desc", type = ComponentType.EXTERNAL)
public class NunamKafka implements TbNode{
	
	private static final Logger logger = Logger.getLogger(NunamKafka.class);
	
	
	
	private static  Producer<String, String> producer;
	
	private static Producer<String, String> createProducer() throws IOException {
		if(producer == null) {
			Properties props = new Properties();
			props.put("bootstrap.servers", "ec2-13-232-173-253.ap-south-1.compute.amazonaws.com:9092");
			props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
			props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
			producer = new KafkaProducer<String, String>(props);
		}
        return producer;
    }
	
	@Override
	public void init(TbContext ctx, TbNodeConfiguration configuration) throws TbNodeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMsg(TbContext ctx, TbMsg msg) throws ExecutionException, InterruptedException, TbNodeException {
		try {
			Producer<String, String> producer = createProducer();
			String messageString = msg.getData();
			ObjectMapper mapper = new ObjectMapper();
			Message message = mapper.readValue(messageString, Message.class);
			ProducerRecordCreator creator = new ProducerRecordCreator(message);
			creator.process();
			List<ProducerRecord<String, String>> records = creator.getRecords();
			for(ProducerRecord<String, String> record:records) {
				producer.send(record);	
			}
			
		}catch (JsonProcessingException | IllegalArgumentException | IllegalAccessException e) {
			logger.error("Custom Node: message processing failed"+ e.getStackTrace());
			ctx.tellFailure(msg, e);
		} catch (Exception e) {
			logger.error("Custom Node: message processing failed"+ e.getStackTrace());
			ctx.tellFailure(msg, e);
		}
		ctx.tellSuccess(msg);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}

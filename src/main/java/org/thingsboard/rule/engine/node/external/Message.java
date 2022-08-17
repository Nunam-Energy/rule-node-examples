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


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Message {

	@JsonProperty("ts")
	private Long ts;
	
	@JsonProperty("deviceId")
	private String deviceId;
	
	@JsonProperty("B0I")
	private String B0I;
	
	@JsonProperty("B0T1")
	private String B0T1;
	@JsonProperty("B0T2")
	private String B0T2;
	
	@JsonProperty("B0V1")
	private String B0V1;
	@JsonProperty("B0V2")
	private String B0V2;
	@JsonProperty("B0V3")
	private String B0V3;
	@JsonProperty("B0V4")
	private String B0V4;
	@JsonProperty("B0V5")
	private String B0V5;
	@JsonProperty("B0V6")
	private String B0V6;
	@JsonProperty("B0V7")
	private String B0V7;
	@JsonProperty("B0V8")
	private String B0V8;
	@JsonProperty("B0V9")
	private String B0V9;
	@JsonProperty("B0V10")
	private String B0V10;
	@JsonProperty("B0V11")
	private String B0V11;
	@JsonProperty("B0V12")
	private String B0V12;
	@JsonProperty("B0V13")
	private String B0V13;
	@JsonProperty("B0V14")
	private String B0V14;
	@JsonProperty("B0V15")
	private String B0V15;
	@JsonProperty("B0V16")
	private String B0V16;
	@JsonProperty("B0V17")
	private String B0V17;
	@JsonProperty("B0V18")
	private String B0V18;
	@JsonProperty("B0V19")
	private String B0V19;
	@JsonProperty("B0V20")
	private String B0V20;
	
	
	@JsonProperty("B0Vp")
	private String B0Vp;
	
	@JsonProperty("B0SOC")
	private String B0SOC;
	
	@JsonProperty("B0Q")
	private String B0Q;
	
	
	
}

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

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemperatureSeries implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8373363406351423325L;
	
	@JsonProperty("tmp_1")
	private String tmp1;
	@JsonProperty("tmp_2")
	private String tmp2;
	@JsonProperty("tmp_3")
	private String tmp3;
	@JsonProperty("tmp_4")
	private String tmp4;
	@JsonProperty("tmp_5")
	private String tmp5;
	@JsonProperty("tmp_6")
	private String tmp6;
	@JsonProperty("tmp_7")
	private String tmp7;
	@JsonProperty("tmp_8")
	private String tmp8;
	@JsonProperty("tmp_9")
	private String tmp9;
	@JsonProperty("tmp_10")
	private String tmp10;
}

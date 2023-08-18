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

	// Module 0

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

	// Module 1

	@JsonProperty("B1I")
	private String B1I;

	@JsonProperty("B1T1")
	private String B1T1;
	@JsonProperty("B1T2")
	private String B1T2;

	@JsonProperty("B1V1")
	private String B1V1;
	@JsonProperty("B1V2")
	private String B1V2;
	@JsonProperty("B1V3")
	private String B1V3;
	@JsonProperty("B1V4")
	private String B1V4;
	@JsonProperty("B1V5")
	private String B1V5;
	@JsonProperty("B1V6")
	private String B1V6;
	@JsonProperty("B1V7")
	private String B1V7;
	@JsonProperty("B1V8")
	private String B1V8;
	@JsonProperty("B1V9")
	private String B1V9;
	@JsonProperty("B1V10")
	private String B1V10;
	@JsonProperty("B1V11")
	private String B1V11;
	@JsonProperty("B1V12")
	private String B1V12;
	@JsonProperty("B1V13")
	private String B1V13;
	@JsonProperty("B1V14")
	private String B1V14;
	@JsonProperty("B1V15")
	private String B1V15;
	@JsonProperty("B1V16")
	private String B1V16;
	@JsonProperty("B1V17")
	private String B1V17;
	@JsonProperty("B1V18")
	private String B1V18;
	@JsonProperty("B1V19")
	private String B1V19;
	@JsonProperty("B1V20")
	private String B1V20;

	@JsonProperty("B1Vp")
	private String B1Vp;

	@JsonProperty("B1SOC")
	private String B1SOC;

	@JsonProperty("B1Q")
	private String B1Q;

	// Module 2

	@JsonProperty("B2I")
	private String B2I;

	@JsonProperty("B2T1")
	private String B2T1;
	@JsonProperty("B2T2")
	private String B2T2;

	@JsonProperty("B2V1")
	private String B2V1;
	@JsonProperty("B2V2")
	private String B2V2;
	@JsonProperty("B2V3")
	private String B2V3;
	@JsonProperty("B2V4")
	private String B2V4;
	@JsonProperty("B2V5")
	private String B2V5;
	@JsonProperty("B2V6")
	private String B2V6;
	@JsonProperty("B2V7")
	private String B2V7;
	@JsonProperty("B2V8")
	private String B2V8;
	@JsonProperty("B2V9")
	private String B2V9;
	@JsonProperty("B2V10")
	private String B2V10;
	@JsonProperty("B2V11")
	private String B2V11;
	@JsonProperty("B2V12")
	private String B2V12;
	@JsonProperty("B2V13")
	private String B2V13;
	@JsonProperty("B2V14")
	private String B2V14;
	@JsonProperty("B2V15")
	private String B2V15;
	@JsonProperty("B2V16")
	private String B2V16;
	@JsonProperty("B2V17")
	private String B2V17;
	@JsonProperty("B2V18")
	private String B2V18;
	@JsonProperty("B2V19")
	private String B2V19;
	@JsonProperty("B2V20")
	private String B2V20;

	@JsonProperty("B2Vp")
	private String B2Vp;

	@JsonProperty("B2SOC")
	private String B2SOC;

	@JsonProperty("B2Q")
	private String B2Q;

	// Module 3

	@JsonProperty("B3I")
	private String B3I;

	@JsonProperty("B3T1")
	private String B3T1;
	@JsonProperty("B3T2")
	private String B3T2;

	@JsonProperty("B3V1")
	private String B3V1;
	@JsonProperty("B3V2")
	private String B3V2;
	@JsonProperty("B3V3")
	private String B3V3;
	@JsonProperty("B3V4")
	private String B3V4;
	@JsonProperty("B3V5")
	private String B3V5;
	@JsonProperty("B3V6")
	private String B3V6;
	@JsonProperty("B3V7")
	private String B3V7;
	@JsonProperty("B3V8")
	private String B3V8;
	@JsonProperty("B3V9")
	private String B3V9;
	@JsonProperty("B3V10")
	private String B3V10;
	@JsonProperty("B3V11")
	private String B3V11;
	@JsonProperty("B3V12")
	private String B3V12;
	@JsonProperty("B3V13")
	private String B3V13;
	@JsonProperty("B3V14")
	private String B3V14;
	@JsonProperty("B3V15")
	private String B3V15;
	@JsonProperty("B3V16")
	private String B3V16;
	@JsonProperty("B3V17")
	private String B3V17;
	@JsonProperty("B3V18")
	private String B3V18;
	@JsonProperty("B3V19")
	private String B3V19;
	@JsonProperty("B3V20")
	private String B3V20;

	@JsonProperty("B3Vp")
	private String B3Vp;

	@JsonProperty("B3SOC")
	private String B3SOC;

	@JsonProperty("B3Q")
	private String B3Q;

	// Module 4

	@JsonProperty("B4I")
	private String B4I;

	@JsonProperty("B4T1")
	private String B4T1;
	@JsonProperty("B4T2")
	private String B4T2;

	@JsonProperty("B4V1")
	private String B4V1;
	@JsonProperty("B4V2")
	private String B4V2;
	@JsonProperty("B4V3")
	private String B4V3;
	@JsonProperty("B4V4")
	private String B4V4;
	@JsonProperty("B4V5")
	private String B4V5;
	@JsonProperty("B4V6")
	private String B4V6;
	@JsonProperty("B4V7")
	private String B4V7;
	@JsonProperty("B4V8")
	private String B4V8;
	@JsonProperty("B4V9")
	private String B4V9;
	@JsonProperty("B4V10")
	private String B4V10;
	@JsonProperty("B4V11")
	private String B4V11;
	@JsonProperty("B4V12")
	private String B4V12;
	@JsonProperty("B4V13")
	private String B4V13;
	@JsonProperty("B4V14")
	private String B4V14;
	@JsonProperty("B4V15")
	private String B4V15;
	@JsonProperty("B4V16")
	private String B4V16;
	@JsonProperty("B4V17")
	private String B4V17;
	@JsonProperty("B4V18")
	private String B4V18;
	@JsonProperty("B4V19")
	private String B4V19;
	@JsonProperty("B4V20")
	private String B4V20;

	@JsonProperty("B4Vp")
	private String B4Vp;

	@JsonProperty("B4SOC")
	private String B4SOC;

	@JsonProperty("B4Q")
	private String B4Q;

	// No Modules

	@JsonProperty("Ip")
	private String Ip;

	@JsonProperty("T1")
	private String T1;
	@JsonProperty("T2")
	private String T2;
	@JsonProperty("T3")
	private String T3;
	@JsonProperty("T4")
	private String T4;
	
	@JsonProperty("BT1")
	private String BT1;
	@JsonProperty("BT2")
	private String BT2;
	@JsonProperty("BT3")
	private String BT3;
	@JsonProperty("BT4")
	private String BT4;

	@JsonProperty("V1")
	private String V1;
	@JsonProperty("V2")
	private String V2;
	@JsonProperty("V3")
	private String V3;
	@JsonProperty("V4")
	private String V4;
	@JsonProperty("V5")
	private String V5;
	@JsonProperty("V6")
	private String V6;
	@JsonProperty("V7")
	private String V7;
	@JsonProperty("V8")
	private String V8;
	@JsonProperty("V9")
	private String V9;
	@JsonProperty("V10")
	private String V10;
	@JsonProperty("V11")
	private String V11;
	@JsonProperty("V12")
	private String V12;
	@JsonProperty("V13")
	private String V13;
	@JsonProperty("V14")
	private String V14;
	@JsonProperty("V15")
	private String V15;
	@JsonProperty("V16")
	private String V16;
	@JsonProperty("V17")
	private String V17;
	@JsonProperty("V18")
	private String V18;
	@JsonProperty("V19")
	private String V19;
	@JsonProperty("V20")
	private String V20;

	@JsonProperty("Vp")
	private String Vp;

	@JsonProperty("SoC")
	private String SoC;

	@JsonProperty("Q")
	private String Q;

}

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

import org.thingsboard.rule.engine.api.NodeConfiguration;

import lombok.Data;

@Data
public class NunamKafkaConfiguration  implements NodeConfiguration<NunamKafkaConfiguration>{

	private String host;
	@Override
	public NunamKafkaConfiguration defaultConfiguration() {
		NunamKafkaConfiguration configuration = new NunamKafkaConfiguration();
		configuration.setHost("");
		return configuration;
	}

}

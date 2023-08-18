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

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;


public class TransformerServiceTest {

	@Test
	public void TestGetCustomerId() {
		TransformerService service = new TransformerService();
		Integer customerId = service.getCustomerId("140cd230-0e3a-11ed-9bda-0163f5a9393b");
		Assert.assertEquals(2+"", customerId+"");
	}
}

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

public class KeyMap {

	private Integer profileId;
	private String ic;
	private String id;
	private String lv;
	private String soc;
	private String tmp;
	private String tmpSeries;
	private String strV;
	private String capacityDischarge;
	
	public Integer getProfileId() {
		return profileId;
	}
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}
	public String getIc() {
		return ic;
	}
	public void setIc(String ic) {
		this.ic = ic;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLv() {
		return lv;
	}
	public void setLv(String lv) {
		this.lv = lv;
	}
	public String getSoc() {
		return soc;
	}
	public void setSoc(String soc) {
		this.soc = soc;
	}
	public String getTmp() {
		return tmp;
	}
	public void setTmp(String tmp) {
		this.tmp = tmp;
	}
	public String getTmpSeries() {
		return tmpSeries;
	}
	public void setTmpSeries(String tmpSeries) {
		this.tmpSeries = tmpSeries;
	}
	public String getStrV() {
		return strV;
	}
	public void setStrV(String strV) {
		this.strV = strV;
	}
	public String getCapacityDischarge() {
		return capacityDischarge;
	}
	public void setCapacityDischarge(String capacityDischarge) {
		this.capacityDischarge = capacityDischarge;
	}
	
	
}

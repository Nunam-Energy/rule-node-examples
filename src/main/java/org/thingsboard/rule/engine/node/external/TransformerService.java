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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TransformerService {

	private static final String TRANSFORMER_HOST = "http://3.111.151.104:8085";
	private static final String END_POINT = "/api/batteries/";
	private static final String USER_AGENT = "Mozilla/5.0";


	private static Map<String, Integer> customerMap = new HashMap<>();

	public Integer getCustomerId(String batteryId) {
		try {
			Integer customerId = customerMap.get(batteryId);
			if (customerId == null) {
				
				URL obj = new URL(TRANSFORMER_HOST + END_POINT + batteryId);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", USER_AGENT);
				int responseCode = con.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) { // success
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();

					// print result
					ObjectMapper mapper = new ObjectMapper();
					BatteryResp battery = mapper.readValue(response.toString(), BatteryResp.class);
					customerId = battery.getCustomerId();
					if (customerId == null) {
						return null;
					}
					customerMap.put(batteryId, customerId);
				} else {
					return null;
				}
				
			}
			return customerId;
		} catch (Exception e) {
			return null;
		}

	}

}

class BatteryResp {

	private UUID id;
	private String name;
	private Integer profileId;
	private Integer customerId;
	private Long dotId;
	private Integer batteryOEMId;
	private String batteryId;
	private Integer bmsId; 

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Long getDotId() {
		return dotId;
	}

	public void setDotId(Long dotId) {
		this.dotId = dotId;
	}

	public Integer getBatteryOEMId() {
		return batteryOEMId;
	}

	public void setBatteryOEMId(Integer batteryOEMId) {
		this.batteryOEMId = batteryOEMId;
	}

	public String getBatteryId() {
		return batteryId;
	}

	public void setBatteryId(String batteryId) {
		this.batteryId = batteryId;
	}

	public Integer getBmsId() {
		return bmsId;
	}

	public void setBmsId(Integer bmsId) {
		this.bmsId = bmsId;
	}

}
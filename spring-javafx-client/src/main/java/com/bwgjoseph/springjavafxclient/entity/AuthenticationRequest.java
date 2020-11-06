package com.bwgjoseph.springjavafxclient.entity;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
	
	private String strategy;
	private String email;
	private String password;
	
	public JSONObject toJSONObject() throws JsonProcessingException, JSONException {
		ObjectMapper om = new ObjectMapper();
		return new JSONObject(om.writeValueAsString(this));
	}
}

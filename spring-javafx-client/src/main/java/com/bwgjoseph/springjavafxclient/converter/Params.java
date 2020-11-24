package com.bwgjoseph.springjavafxclient.converter;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Params {

	public JSONObject toJSONObject() throws JsonProcessingException, JSONException {
		ObjectMapper om = new ObjectMapper();
		return new JSONObject(om.writeValueAsString(this));
	}
}

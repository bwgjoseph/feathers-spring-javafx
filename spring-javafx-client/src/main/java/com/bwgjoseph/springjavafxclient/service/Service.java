package com.bwgjoseph.springjavafxclient.service;

import java.util.List;

import org.json.JSONException;
import com.bwgjoseph.springjavafxclient.entity.User;

public interface Service {
	List<User> find();
	void create() throws JSONException;
	void remove(String userId);
}

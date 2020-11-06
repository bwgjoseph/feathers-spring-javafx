package com.bwgjoseph.springjavafxclient.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bwgjoseph.springjavafxclient.application.FeathersClient;
import com.bwgjoseph.springjavafxclient.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.socket.client.Ack;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements com.bwgjoseph.springjavafxclient.service.Service {

	private static final String USER_SERVICE = "users";
	private final FeathersClient feathersClient;
	private List<User> userList = new ArrayList<>();

	public UserService(FeathersClient feathersClient) {
		Assert.notNull(feathersClient, "feathersClient cannot be null");

		this.feathersClient = feathersClient;
	}
	
	public List<User> find() {
		// This way, it makes it flexible to let caller perform whatever they want on the callback
		this.feathersClient.find(USER_SERVICE, onResult("Find"));
		// Will only get the list the 2nd time it gets called
		return this.userList;
	}

	public void create() throws JSONException {
		try {
			// try using factory method?
			// https://www.logicbig.com/tutorials/misc/jackson/json-creator.html
			// https://sharing.luminis.eu/blog/flexible-immutability-with-jackson-and-lombok/
			JSONObject newUser = User.builder()
					._id("1221")
					.email("venago2680@insertswork.com")
					.password("password")
					.build()
					.toJSONObject();
			
			this.feathersClient.create(USER_SERVICE, newUser, onResult("Create"));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    }

	public void remove(String userId) {
		this.feathersClient.remove(USER_SERVICE, userId, onResult("Remove"));
	}
	
	// Version 2
	private Ack onResult(String event) {
		return result -> {
		  if (result[0] != null) {
			  log.error("An error has occurred: " + result[0].toString());
			  return;
		  }

		  try {
		    String prettyAuth = null;
			ObjectMapper mapper = new ObjectMapper();
			if ("Find".equals(event)) {
				userList = Arrays.asList(mapper.readValue(result[1].toString(), User[].class));
				prettyAuth = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userList);
			} else {
				User user = mapper.readValue(result[1].toString(), User.class);
				prettyAuth = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
			}
			
			log.info("onUser" + event + ": " + prettyAuth);
		  } catch (JsonProcessingException e) {
			e.printStackTrace();
		  }
		};
    }

	// Version 1
//	private Ack onFind() {
//		return result -> {
//		  if (result[0] != null) {
//			  log.error("An error has occurred: " + result[0].toString());
//			  return;
//		  }
//
//		  try {
//			ObjectMapper mapper = new ObjectMapper();
//			userList = Arrays.asList(mapper.readValue(result[1].toString(), User[].class));
//			String prettyAuth = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userList);
//			log.info("onFind: " + prettyAuth);
//		  } catch (JsonProcessingException e) {
//			e.printStackTrace();
//		  }
//		};
//    }
//	
//	private Ack onCreate() {
//		return result -> {
//		  if (result[0] != null) {
//			  log.error("An error has occurred: " + result[0].toString());
//			  return;
//		  }
//
//		  try {
//			ObjectMapper mapper = new ObjectMapper();
//			User user = mapper.readValue(result[1].toString(), User.class);
//			String prettyAuth = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
//			log.info("onRegister: " + prettyAuth);
//		  } catch (JsonProcessingException e) {
//			e.printStackTrace();
//		  }
//		};
//	}
//
//	private Ack onRemove() {
//		return result -> {
//		  if (result[0] != null) {
//			  log.error("An error has occurred: " + result[0].toString());
//			  return;
//		  }
//
//		  try {
//			ObjectMapper mapper = new ObjectMapper();
//			User user = mapper.readValue(result[1].toString(), User.class);
//			String prettyAuth = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
//			log.info("onRemove: " + prettyAuth);
//		  } catch (JsonProcessingException e) {
//			e.printStackTrace();
//		  }
//		};
//    }
}

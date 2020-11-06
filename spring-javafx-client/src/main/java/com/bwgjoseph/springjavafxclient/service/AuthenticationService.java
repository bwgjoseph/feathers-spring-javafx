package com.bwgjoseph.springjavafxclient.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bwgjoseph.springjavafxclient.application.FeathersClient;
import com.bwgjoseph.springjavafxclient.entity.AuthenticationRequest;
import com.bwgjoseph.springjavafxclient.entity.AuthenticationResponse;
import com.bwgjoseph.springjavafxclient.event.publisher.UserEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.socket.client.Ack;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationService {
	private final FeathersClient feathersClient;
	private final UserEventPublisher userEventPublisher;
	
	public AuthenticationService(FeathersClient feathersClient, UserEventPublisher userEventPublisher) {
		Assert.notNull(feathersClient, "feathersClient cannot be null");
		Assert.notNull(userEventPublisher, "userEventPublisher cannot be null");
		
		this.feathersClient = feathersClient;
		this.userEventPublisher = userEventPublisher;
	}
	
	public void authenticateLocal() throws JSONException {
		try {
			JSONObject authenticationRequest = AuthenticationRequest.builder()
					.strategy("local")
					.email("venago2680@insertswork.com")
					.password("password")
					.build()
					.toJSONObject();
			
			this.feathersClient.create("authentication", authenticationRequest, onLogin());
//			feathersClient.getSocket().on("authentication created", args -> System.out.println("authentication created" + args[0]));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	private Ack onLogin() {
		return result -> {
		  try {
			if (result[0] != null) {
				log.error("An error has occurred: " + result[0].toString());
				return;
			}
			  
			ObjectMapper mapper = new ObjectMapper();

			AuthenticationResponse auth = mapper.readValue(result[1].toString(), AuthenticationResponse.class);
			String prettyAuth = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(auth);
			log.info(prettyAuth);
			this.userEventPublisher.publishUserLoginEvent(auth);
		  } catch (JsonProcessingException e) {
			e.printStackTrace();
		  }
		};
	}
}

package com.bwgjoseph.springjavafxclient.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bwgjoseph.springjavafxclient.application.FeathersClient;
import com.bwgjoseph.springjavafxclient.entity.AuthenticationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.socket.client.Ack;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationService {
	private final FeathersClient feathersClient;
	
	public AuthenticationService(FeathersClient feathersClient) {
		Assert.notNull(feathersClient, "feathersClient cannot be null");
		
		this.feathersClient = feathersClient;
	}
	
	public void authenticateLocal() throws JSONException {
		JSONObject authenticationInfo = new JSONObject();
		authenticationInfo.put("strategy", "local");
		authenticationInfo.put("email", "venago2680@insertswork.com");
		authenticationInfo.put("password", "password");
		
		this.feathersClient.create("authentication", authenticationInfo, onLogin());
//		feathersClient.getSocket().on("authentication created", args -> System.out.println("authentication created" + args[0]));
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
			Authentication authentication = new UsernamePasswordAuthenticationToken(auth.getUser(), null,
	                AuthorityUtils.createAuthorityList("ROLE_USER"));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        log.info("authentication: " + authentication);
		  } catch (JsonProcessingException e) {
			e.printStackTrace();
		  }
		};
	}
}

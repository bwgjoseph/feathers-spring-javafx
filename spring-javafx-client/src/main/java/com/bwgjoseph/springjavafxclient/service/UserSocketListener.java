package com.bwgjoseph.springjavafxclient.service;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bwgjoseph.springjavafxclient.application.FeathersClient;
import com.bwgjoseph.springjavafxclient.entity.User;
import com.bwgjoseph.springjavafxclient.event.WebSocketReadyEvent;
import com.bwgjoseph.springjavafxclient.event.publisher.GenericCreatedEventPublisher;
import com.bwgjoseph.springjavafxclient.event.publisher.UserEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.socket.emitter.Emitter.Listener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserSocketListener implements ApplicationListener<WebSocketReadyEvent> {

	private static final String USER_SERVICE = "users";
	private final FeathersClient feathersClient;
	
	// Here we support using either per Entity event publisher
	private final UserEventPublisher userEventPublisher;
	// Or per Type (Created, Updated, Removed) event publisher
	private final GenericCreatedEventPublisher genericCreatedEventPublisher;
	
	public UserSocketListener(FeathersClient feathersClient, UserEventPublisher userEventPublisher, GenericCreatedEventPublisher genericCreatedEventPublisher) {
		Assert.notNull(feathersClient, "feathersClient cannot be null");
		Assert.notNull(userEventPublisher, "userEventPublisher cannot be null");
		Assert.notNull(genericCreatedEventPublisher, "genericCreatedEventPublisher cannot be null");
		
		this.feathersClient = feathersClient;
		this.userEventPublisher = userEventPublisher;
		this.genericCreatedEventPublisher = genericCreatedEventPublisher;
	}
	
	@Override
	public void onApplicationEvent(WebSocketReadyEvent event) {
		log.info("UserSocketListener listen only after websocket is ready");
		// Only starts listening to websocket events after we ensure WebSocketReadyEvent is received
		this.feathersClient.listen(USER_SERVICE, "created", onUser("Created"));
		this.feathersClient.listen(USER_SERVICE, "patched", onUser("Patched"));
		this.feathersClient.listen(USER_SERVICE, "removed", onUser("Removed"));
	}
	
	private User mapToUser(String result, String event) {
		try {
	      ObjectMapper mapper = new ObjectMapper();
	      User user = mapper.readValue(result, User.class);
		  String prettyAuth = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
		  log.info("onUser" + event + ": " + prettyAuth);
		  
		  return user;
	    } catch (JsonProcessingException e) {
	      e.printStackTrace();
	    }
		return null;
	}
	
	// Version 3 - Take in `method` arg to determine to flow using switch
	private Listener onUser(String method) {
		return result -> {
	      User user = this.mapToUser(result[0].toString(), method);
	      
	      if (user != null) {
	    	  switch (method) {
		    	  case "Created": 
		    		  // Once published, anyone can subscribe/listen to this UserCreatedEvent and react to it
		    		  userEventPublisher.publishUserCreatedEvent(user);
		    		  genericCreatedEventPublisher.publishCreatedEvent(user);
		    		  break;
		    	  case "Patched": 
		    		  userEventPublisher.publishUserPatchedEvent(user);
		    		  break;
		    	  case "Removed": 
		    		  userEventPublisher.publishUserRemovedEvent(user);
		    		  break;
	    		  default:
	    			  log.error("Should not come here");
	    	  }
	      }
		};
	}
	
	// Version 2 - Refactored to extract mapToUser
//	private Listener onUserCreated() {
//		return result -> {
//	      User user = this.mapToUser(result[0].toString(), "Created");
//	      
//	      if (user != null) {
//	    	  userEventPublisher.publishUserCreatedEvent(user);
//			  genericCreatedEventPublisher.publishCreatedEvent(user);
//	      }
//		};
//	}
//	
//	private Listener onUserPatched() {
//		return result -> {
//    	  User user = this.mapToUser(result[0].toString(), "Updated");
//	      
//	      if (user != null) {
//	    	  userEventPublisher.publishUserPatchedEvent(user);
//	      }
//		};
//	}
//	
//	private Listener onUserRemoved() {
//		return result -> {
//    	  User user = this.mapToUser(result[0].toString(), "Removed");
//	      
//	      if (user != null) {
//	    	  userEventPublisher.publishUserRemovedEvent(user);
//	      }
//		};
//	}

	// Version 1
//	private Listener onUserCreated() {
//		return result -> {
//		    try {
//		      ObjectMapper mapper = new ObjectMapper();
//		      User user = mapper.readValue(result[0].toString(), User.class);
//			  String prettyAuth = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
//			  log.info("onUserCreated: " + prettyAuth);
//			  userEventPublisher.publishUserCreatedEvent(user);
//			  genericCreatedEventPublisher.publishCreatedEvent(user);
//		    } catch (JsonProcessingException e) {
//		      e.printStackTrace();
//		    }
//		};
//	}
	
//	private Listener onUserPatched() {
//		return result -> {
//		    try {
//		      ObjectMapper mapper = new ObjectMapper();
//		      User user = mapper.readValue(result[0].toString(), User.class);
//			  String prettyAuth = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
//			  log.info("onUserPatched: " + prettyAuth);
//			  userEventPublisher.publishUserPatchedEvent(user);
//		    } catch (JsonProcessingException e) {
//		      e.printStackTrace();
//		    }
//		};
//	}
//	
//	private Listener onUserRemoved() {
//		return result -> {
//		    try {
//		      ObjectMapper mapper = new ObjectMapper();
//		      User user = mapper.readValue(result[0].toString(), User.class);
//			  String prettyAuth = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
//			  log.info("onUserRemoved: " + prettyAuth);
//			  userEventPublisher.publishUserRemovedEvent(user);
//		    } catch (JsonProcessingException e) {
//		      e.printStackTrace();
//		    }
//		};
//	}
}

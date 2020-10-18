package com.bwgjoseph.springjavafxclient.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bwgjoseph.springjavafxclient.entity.User;
import com.bwgjoseph.springjavafxclient.event.UserCreatedEvent;
import com.bwgjoseph.springjavafxclient.event.UserRemovedEvent;
import com.bwgjoseph.springjavafxclient.event.UserPatchedEvent;

@Component
public class UserEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;
	
	public UserEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		Assert.notNull(applicationEventPublisher, "applicationEventPublisher cannot be null");
		
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishUserCreatedEvent(User user) {
		UserCreatedEvent uce = new UserCreatedEvent(this, user);
		this.applicationEventPublisher.publishEvent(uce);
	}
	
	public void publishUserPatchedEvent(User user) {
		UserPatchedEvent upe = new UserPatchedEvent(this, user);
		this.applicationEventPublisher.publishEvent(upe);
	}
	
	public void publishUserRemovedEvent(User user) {
		UserRemovedEvent ure = new UserRemovedEvent(this, user);
		this.applicationEventPublisher.publishEvent(ure);
	}
}

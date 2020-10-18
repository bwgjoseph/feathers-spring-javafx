package com.bwgjoseph.springjavafxclient.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bwgjoseph.springjavafxclient.entity.User;
import com.bwgjoseph.springjavafxclient.event.GenericCreatedEvent;

@Component
public class GenericCreatedEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;
	
	public GenericCreatedEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		Assert.notNull(applicationEventPublisher, "applicationEventPublisher cannot be null");
		
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishCreatedEvent(User user) {
		GenericCreatedEvent<User> gce = new GenericCreatedEvent<User>(this, user);
		this.applicationEventPublisher.publishEvent(gce);
	}
}

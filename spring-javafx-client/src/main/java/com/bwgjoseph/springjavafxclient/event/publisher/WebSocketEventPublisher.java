package com.bwgjoseph.springjavafxclient.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bwgjoseph.springjavafxclient.event.WebSocketReadyEvent;

@Component
public class WebSocketEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;
	
	public WebSocketEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		Assert.notNull(applicationEventPublisher, "applicationEventPublisher cannot be null");
		
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishWebSocketEvent(boolean connected) {
		WebSocketReadyEvent wsre = new WebSocketReadyEvent(this, connected);
		this.applicationEventPublisher.publishEvent(wsre);
	}
}

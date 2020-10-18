package com.bwgjoseph.springjavafxclient.event.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bwgjoseph.springjavafxclient.event.WebSocketReadyEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSocketEventListener implements ApplicationListener<WebSocketReadyEvent> {
	
	@Override
	public void onApplicationEvent(WebSocketReadyEvent event) {
		// We can probably run some code to pull certain/all data from server after this
		log.info("Run after WebSocketReadyEvent " + event.getConnected());
	}

}

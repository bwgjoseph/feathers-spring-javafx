package com.bwgjoseph.springjavafxclient.event;

import org.springframework.context.ApplicationEvent;

public class WebSocketReadyEvent extends ApplicationEvent {
	private static final long serialVersionUID = 2285023829331193590L;
	
	private boolean connected;

	public WebSocketReadyEvent(Object source, boolean connected) {
		super(source);
		this.connected = connected;
	}
	
	public boolean getConnected() {
		return this.connected;
	}
}

package com.bwgjoseph.springjavafxclient.event;

import org.springframework.context.ApplicationEvent;

import com.bwgjoseph.springjavafxclient.entity.AuthenticationResponse;

public class UserLoginEvent extends ApplicationEvent {
	private static final long serialVersionUID = -8418949701278407792L;
	private AuthenticationResponse authenticationResponse;

	public UserLoginEvent(Object source, AuthenticationResponse authenticationResponse) {
		super(source);
		this.authenticationResponse = authenticationResponse;
	}

	public AuthenticationResponse getAuthenticationResponse() {
		return this.authenticationResponse;
	}
}
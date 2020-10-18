package com.bwgjoseph.springjavafxclient.entity;

import lombok.Data;

@Data
public class AuthenticationResponse {

	private String accessToken;
	private Authentication authentication;
	private User user;
	
}

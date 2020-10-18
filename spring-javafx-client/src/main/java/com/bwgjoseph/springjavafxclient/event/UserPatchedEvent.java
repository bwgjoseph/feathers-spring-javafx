package com.bwgjoseph.springjavafxclient.event;

import org.springframework.context.ApplicationEvent;

import com.bwgjoseph.springjavafxclient.entity.User;

public class UserPatchedEvent extends ApplicationEvent {
	private static final long serialVersionUID = -5712906163782501217L;
	private User user;

	public UserPatchedEvent(Object source, User user) {
		super(source);
		this.user = user;
	}
	
	public String getUserId() {
		return this.user.get_id();
	}
	
	public User getUser() {
		return this.user;
	}
}

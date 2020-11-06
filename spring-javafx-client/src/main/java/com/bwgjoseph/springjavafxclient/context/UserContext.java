package com.bwgjoseph.springjavafxclient.context;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.bwgjoseph.springjavafxclient.entity.User;

public interface UserContext {
	User getCurrentUser();
	List<GrantedAuthority> getUserGrantedAuthority();
}

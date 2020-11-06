package com.bwgjoseph.springjavafxclient.context;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.bwgjoseph.springjavafxclient.entity.User;

@Component
public class AuthenticatedPrincipalUserContext implements UserContext {

	@Override
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : (User) authentication.getPrincipal();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrantedAuthority> getUserGrantedAuthority() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : (List<GrantedAuthority>) authentication.getAuthorities();
	}

}

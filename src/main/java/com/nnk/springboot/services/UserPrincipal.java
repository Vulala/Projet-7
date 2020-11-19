package com.nnk.springboot.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nnk.springboot.domain.User;

/**
 * Store the user information which is then encapsulated into an Authentication
 * object, {@link SecurityConfig}. <br>
 * It implements the {@link UserDetails} interface.
 */

public class UserPrincipal implements UserDetails {

	/**
	 * serialVersionUID requested by the UserPrincipal
	 */
	private static final long serialVersionUID = 7341116959534490270L;
	private User user;

	public UserPrincipal(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

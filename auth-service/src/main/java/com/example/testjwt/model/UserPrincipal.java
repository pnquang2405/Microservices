package com.example.testjwt.model;


import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;


public class UserPrincipal implements UserDetails{
	private static final long serialVersionUID = 1L;
	private Long userId;
    private String username;
    private String password;
    private Collection<String> authorities;
    
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Collection getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities2) {
		this.authorities = authorities2;
	}
	
	
}

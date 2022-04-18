package com.oikostechnologies.schedsys.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.entity.UserRole;

import lombok.Getter;


@Getter
public class MyUserDetails implements UserDetails {
	
	private User user;
	
	public MyUserDetails(User user) {
		this.user = user;
	}
	
	public String getFullname() {
		return this.user.fullname();
	}
	
	public String getRolename() {
		return this.user.role();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> grantedauthorities = new ArrayList<>();	
		for(UserRole ur : user.getUserrole()) {
			grantedauthorities.add(new SimpleGrantedAuthority(ur.getRole().getRolename()));
		}
		return grantedauthorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return String.valueOf(user.getPassword());
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.isEnabled();
	}

}

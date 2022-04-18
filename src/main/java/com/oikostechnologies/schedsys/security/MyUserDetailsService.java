package com.oikostechnologies.schedsys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepo userrepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userrepo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("No user with that email");
		}
		return new MyUserDetails(user);
	}

}

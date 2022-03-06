package com.oikostechnologies.schedsys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.repo.UserRepo;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepo userrepo;
	
	@Override
	public long usercount() {
		// TODO Auto-generated method stub
		return userrepo.count();
	}

	public List<User> getUsersByRole(long role){
		return userrepo.getUsersByRole(role);
	}

	@Override
	public List<User> findAllUsers() {
		
		return userrepo.findAll();
	}

	
	
}

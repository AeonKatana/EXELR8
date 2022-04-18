package com.oikostechnologies.schedsys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.security.MyUserDetails;

public interface UserService {

	public long usercount();
	public Page<User> getUsersByRole(String role);
	public List<User> findAllUsers();
	public Page<User> findAllUsers2();
	public Page<User> findAllUsers(int page);
	public Page<User> findAllUsers(int page , String search);
	public Page<User> searchUser(String search);
	public DataTablesOutput<User> findAll(DataTablesInput input);
	public void saveRegistrationToken(User user , String token);
	public String findByEmail(String email, HttpServletRequest request);
	public void savePasswordToken(User user , String token);
	public List<User> getAllByCompany(MyUserDetails user);
	public User findById(long id);
	public List<User> getAllByCompany(String name);
}

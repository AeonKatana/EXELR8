package com.oikostechnologies.schedsys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import com.oikostechnologies.schedsys.datatable.repo.UserDataTable;
import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.PasswordToken;
import com.oikostechnologies.schedsys.entity.RegistrationToken;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.event.PasswordEvent;
import com.oikostechnologies.schedsys.repo.PasswordTokenRepo;
import com.oikostechnologies.schedsys.repo.RegistrationTokenRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private UserDataTable usertablerepo;
	
	@Autowired
	private RegistrationTokenRepo tokenrepo;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PasswordTokenRepo ptokenrepo;
	
	@Override
	public long usercount() {
		// TODO Auto-generated method stub
		return userrepo.count();
	}

	public Page<User> getUsersByRole(String role){
		return userrepo.getUsersByRole(role, PageRequest.of(0, 5));
	}

	@Override
	public List<User> findAllUsers() {
		return userrepo.findAll();
	}

	
	
	@Override
	public Page<User> searchUser(String search) {
		
		return userrepo.findByFirstnameContainingOrLastnameContaining(search, search , PageRequest.of(0, 5));
	}

	@Override
	public Page<User> findAllUsers(int page) {
		return userrepo.findAll(PageRequest.of(page, 5));
	}

	@Override
	public Page<User> findAllUsers(int page, String search) {
		return userrepo.findAllByFirstnameContaining(search , PageRequest.of(page, 5));
	}

	@Override
	public DataTablesOutput<User> findAll(@Valid DataTablesInput input) {
		return usertablerepo.findAll(input);
	}

	@Override
	public void saveRegistrationToken(User user, String token) {
		
		RegistrationToken rtoken = new RegistrationToken();
		rtoken.setToken(token);
		rtoken.setUser(user);
		tokenrepo.save(rtoken);
		
	}

	@Override
	public String findByEmail(String email , HttpServletRequest request) {
		User user = userrepo.findByEmail(email);
		if(user == null) {
			return "User with that email doesn't exist in the system.";
		}
		publisher.publishEvent(new PasswordEvent(user, applicationUrl(request)));
		return "We've sent the request password link! Please check your email";
	}

	private String applicationUrl(HttpServletRequest request) {
		return "https://" + request.getServerName() + request.getContextPath();
	}

	@Override
	public void savePasswordToken(User user, String token) {
		
		PasswordToken ptoken = user.getPasstoken();
		if(ptoken == null) {
			ptoken = new PasswordToken();
		}
		ptoken.setToken(token);
		ptoken.setUser(user);
		ptokenrepo.save(ptoken);
		
	}

	@Override
	public List<User> getAllByCompany(MyUserDetails user) {
		return userrepo.getAllByCompanyname(user.getUser().companyname());
	}

	@Override
	public Page<User> findAllUsers2() {
		return null;
	}

	@Override
	public User findById(long id) {
		return userrepo.findById(id).orElse(null);
	}

	@Override
	public List<User> getAllByCompany(String name) {
		return userrepo.getAllByCompanyname(name);
	}

	
	
}

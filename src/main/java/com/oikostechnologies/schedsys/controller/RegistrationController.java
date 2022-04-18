package com.oikostechnologies.schedsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oikostechnologies.schedsys.entity.RegistrationToken;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.repo.RegistrationTokenRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;

@Controller
public class RegistrationController {
	
	@Autowired
	private RegistrationTokenRepo tokenrepo;
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/verifyUser")
	public String verifyUserPage(Model model, @RequestParam("token") String token) {
		
		RegistrationToken rtoken = tokenrepo.findByToken(token);
		model.addAttribute("token", rtoken);
		return "verifyuser";
	}
	
	@PostMapping("/newUser")
	public String newUser(@RequestParam("password") String password , @RequestParam("token") String token) {
		RegistrationToken rtoken = tokenrepo.findByToken(token);
		User user = rtoken.getUser();
		user.setPassword(passwordEncoder.encode(password).toCharArray());
		user.setEnabled(true);
		userrepo.save(user);
		return "regsuccess";
	}
	
}

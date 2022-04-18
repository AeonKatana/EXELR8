package com.oikostechnologies.schedsys.event;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.oikostechnologies.schedsys.email.EmailService;
import com.oikostechnologies.schedsys.email.ResetPasswordEmail;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.service.UserService;

@Component
public class PasswordEventListener implements ApplicationListener<PasswordEvent>{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;

	@Override
	public void onApplicationEvent(PasswordEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		String url = event.getApplicationUrl() + "/resetPass?token=" + token;
		userService.savePasswordToken(user, token);
		
		ResetPasswordEmail message = new ResetPasswordEmail();
		message.setReceiver(user.getEmail());
		message.setSubject("Password Reset Request");
		message.setMessage(url);
		emailService.sendEmail(message);
		
	}

}

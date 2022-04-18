package com.oikostechnologies.schedsys.event;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.oikostechnologies.schedsys.email.EmailMessage;
import com.oikostechnologies.schedsys.email.EmailService;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.service.UserService;

@Component
public class CompanyEventListener implements ApplicationListener<CompanyEvent>{
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private EmailService emailservice;

	@Override
	public void onApplicationEvent(CompanyEvent event) {
		
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		String url = event.getApplicationUrl() + "/verifyUser?token=" + token;
		
		userservice.saveRegistrationToken(user, token);
		
		EmailMessage message = new EmailMessage();
		message.setUser(event.getMydetails().getFullname());
		message.setReceiver(user.getEmail());
		message.setSubject("Invitation to the Company");
		message.setMessage(url);
		emailservice.sendEmail(message);
	}

}

package com.oikostechnologies.schedsys.controller;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.oikostechnologies.schedsys.entity.Notification;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.model.PeopleModel;
import com.oikostechnologies.schedsys.repo.NotificationRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.websocket.NotifMessage;

@Controller
public class NotificationController {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private NotificationRepo notifrepo;

	@MessageMapping("/sendDone")
	public void sendNotification(Principal principal,@Payload NotifMessage message) {
		System.out.println("Current User is " + principal.getName());
		message.setSender(principal.getName());
		
			User to = userRepo.findById(Long.parseLong(message.getTo())).orElse(null);
			User current = userRepo.findByEmail(principal.getName());
			
			if(to != null && to.getId() != current.getId()) {
				message.setContent(current.fullname() + " has completed a daily task you've assigned");
				template.convertAndSendToUser(to.getEmail(), "/queue/message", message);
			}
			
	}
	@MessageMapping("/sendKick")
	public void sendKick(Principal principal, @Payload NotifMessage message) {
		message.setSender(principal.getName());
		User to = userRepo.findById(Long.parseLong(message.getTo())).orElse(null);
		User current = userRepo.findByEmail(principal.getName());
		message.setContent(current.fullname() + message.getContent());
		template.convertAndSendToUser(to.getEmail(), "/queue/kick", message);
	}
	
	@MessageMapping("/sendAddTask")
	public void sendAddTask(Principal principal, @Payload NotifMessage message) {
		message.setSender(principal.getName());
		User current = userRepo.findByEmail(principal.getName());
		if(message.getTos().size() > 0) {
			for(PeopleModel pm : message.getTos()) {
				
				User to = userRepo.findById(pm.getId()).orElse(null);
				
				if(to != null) {
					message.setContent(current.fullname() + " has assigned you a daily task");
					template.convertAndSendToUser(to.getEmail(), "/queue/message", message);
					
				}
			}
		}
			
	}
}

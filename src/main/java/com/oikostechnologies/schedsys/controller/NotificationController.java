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
				Notification notification = new Notification();
				notification.setAction("has completed a daily task you've assigned");
				notification.setActiontarget(current.fullname());
				notification.setUser(to);
				notification.setDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")));
				notification.setTargetlink("#");
				notifrepo.save(notification);
			}
			
	}
	
}

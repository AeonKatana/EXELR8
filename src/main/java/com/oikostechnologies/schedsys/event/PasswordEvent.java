package com.oikostechnologies.schedsys.event;

import org.springframework.context.ApplicationEvent;

import com.oikostechnologies.schedsys.entity.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordEvent extends  ApplicationEvent{

	
	private User user;
	private String applicationUrl;
	
	public PasswordEvent(User user, String applicationUrl) {
		super(user);
		this.user = user;
		this.applicationUrl = applicationUrl;
	}

}

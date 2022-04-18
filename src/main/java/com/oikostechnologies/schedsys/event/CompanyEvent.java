package com.oikostechnologies.schedsys.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.security.MyUserDetails;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CompanyEvent extends ApplicationEvent{

	private User user;
	private String applicationUrl;
	private MyUserDetails mydetails;
	
	public CompanyEvent(@AuthenticationPrincipal MyUserDetails detail,User user, String applicationUrl) {
		super(user);
		this.user = user;
		this.mydetails = detail;
		this.applicationUrl =  applicationUrl;
	}

}

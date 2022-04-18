package com.oikostechnologies.schedsys.email;

public class EmailMessage {

	private String user;
	private String message;
	private String receiver;
	private String subject;
	
	public void setUser(String user) {
		this.user = user;
	}
	public String getUser() {
		return user;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String formattedEmail() {
		return user + " has invited you to join his company online. Click the link below to accept the invitation" + "\n \n" + message;
	}
	
}

package com.oikostechnologies.schedsys.email;

public class ResetPasswordEmail extends EmailMessage{
	private String user;
	private String message;
	private String receiver;
	private String subject;
	@Override
	public void setUser(String user) {
		this.user = user;
	}
	@Override
	public String getUser() {
		return user;
	}
	@Override
	public String getMessage() {
		return message;
	}
	@Override
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String getReceiver() {
		return receiver;
	}
	@Override
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	@Override
	public String getSubject() {
		return subject;
	}
	@Override
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String formattedEmail() {
		return  "You have requested to reset your password. Click the link below to reset your password" + "\n \n" + message;
	}
}

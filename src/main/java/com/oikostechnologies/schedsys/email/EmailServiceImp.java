package com.oikostechnologies.schedsys.email;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {

	
	@Autowired
	private JavaMailSender mailsender;

	@Override
	public void sendEmail(EmailMessage messages) {
		
		   MimeMessage message = mailsender.createMimeMessage();
		  MimeMessageHelper helper = new MimeMessageHelper(message);
		 
		 
		  try {
		  helper.setFrom(new InternetAddress("oikosemailnotifications@gmail.com","ConnectereHub"));
		  helper.setTo(messages.getReceiver());
		  helper.setSubject(messages.getSubject());
		  helper.setText(messages.formattedEmail());
		  mailsender.send(message);
		  }catch(Exception e) {
			  System.out.println("ERROR!");
		  }
		
	}
	
}

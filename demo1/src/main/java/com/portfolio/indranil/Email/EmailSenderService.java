package com.portfolio.indranil.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String toEmail, String toReply,
							String subject,
							String body) {
		SimpleMailMessage message= new SimpleMailMessage();
		message.setFrom("andybuddy1499@gmail.com");
		message.setSubject(subject);
		message.setTo(toEmail);
		message.setText(body);
		message.setReplyTo(toReply);
		
		mailSender.send(message);
		
		System.out.println(message.toString());
	}

}

package com.example.moattravel.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.moattravel.entity.User;
import com.example.moattravel.service.VerificationTokenService;

@Component

public class SignupEventListener {
	private final VerificationTokenService verificationTokenservice;
	private final JavaMailSender javaMailSender;

	public SignupEventListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender) {
		this.verificationTokenservice = verificationTokenService;
		this.javaMailSender = mailSender;

	}

	@EventListener
	private void onSignupEvent(SignupEvent signupEvent) {
		User user= signupEvent.getUser();
		String token=UUID.randomUUID().toString();
		verificationTokenservice.create(user,token);
		String recipientAddress=user.getEmail();
		String subject="メール認証";
		String confirmationUrl=signupEvent.getRequestUrl()+"/verify?token="+token;
		String message="以下のリンクをクリックして会員登録を完了してください。";
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(recipientAddress);
		mailMessage.setSubject(subject);
		mailMessage.setText(message+"\n"+confirmationUrl);
		javaMailSender.send(mailMessage);
	}
}

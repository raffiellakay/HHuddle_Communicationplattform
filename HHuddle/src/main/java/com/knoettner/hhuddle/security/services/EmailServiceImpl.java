package com.knoettner.hhuddle.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl  {
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(
            String mail, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hhuddle.welcome@gmail.com");
        message.setTo(mail);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

    }
}

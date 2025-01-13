package com.knoettner.hhuddle.controller;


import com.knoettner.hhuddle.security.modelsDtos.EmailDetails;
import com.knoettner.hhuddle.security.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

@Autowired
    private EmailService emailService;

@PostMapping("/reset")
    public void sendMail (@RequestBody EmailDetails details) {
     emailService.sendMailToResetPw(details);

}

}

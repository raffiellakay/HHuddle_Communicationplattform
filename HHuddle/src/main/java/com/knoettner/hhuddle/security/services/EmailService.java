package com.knoettner.hhuddle.security.services;

import com.knoettner.hhuddle.security.modelsDtos.ContactFormDetails;
import com.knoettner.hhuddle.security.modelsDtos.EmailDetails;

public interface EmailService {
    void sendMailToResetPw(EmailDetails details);
    void sendMailFromContactForm(ContactFormDetails details);
}

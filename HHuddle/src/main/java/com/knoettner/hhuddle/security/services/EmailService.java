package com.knoettner.hhuddle.security.services;

import com.knoettner.hhuddle.security.modelsDtos.EmailDetails;

public interface EmailService {
    void sendMailToResetPw(EmailDetails details);

}

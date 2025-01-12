package com.knoettner.hhuddle.security.services;

import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.repository.UserRepository;
import com.knoettner.hhuddle.security.modelsDtos.EmailDetails;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Value("${spring.mail.username}") private String sender;

    @Override
    public void sendMailToResetPw(EmailDetails details) {
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            //Does User exist?
            Optional<MyUser> maybeUser = userRepository.findByMail("raffy.k@gmx.at");
            if (maybeUser.isPresent()) {
                MyUser actualUser = maybeUser.get();
                //Temp PW
                String tempPw = RandomStringUtils.random(8, "0123456789abcdef");
                actualUser.setPassword(encoder.encode(tempPw));

                userRepository.save(actualUser);

                // Setting up necessary details
                mailMessage.setFrom(sender);
                mailMessage.setTo(details.getRecipient());
                mailMessage.setText("Hey du!  \n\n Hier ist dein temporäres Passwort, um dein Passwort dann auf http://localhost:5173/ neu zu setzen! \n\n" + tempPw);
                mailMessage.setSubject("Neu hier oder Passwort vergessen?");

                // Sending the mail
                javaMailSender.send(mailMessage);
                System.out.println("Mail Sent Successfully");
            }
            if(maybeUser.isEmpty()){
                System.out.println("Es wurde kein passender User gefunden.");
            }
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println( "Error while Sending Mail");
        }

    }

}

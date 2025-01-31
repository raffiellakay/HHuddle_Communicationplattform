package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.MyUserDto;
import com.knoettner.hhuddle.dto.mapper.MyUserMapper;
import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.repository.UserRepository;
import com.knoettner.hhuddle.security.modelsDtos.EmailDetails;
import com.knoettner.hhuddle.security.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    MyUserMapper userMapper;

    @Autowired
    EmailService emailService;

    @Override
    public Long getHouseIdByUser(Long userId) {
        Optional<MyUser> maybeUser = userRepository.findById(userId);
        if (maybeUser.isPresent()) {
            MyUser user = maybeUser.get();
            return user.getHouse().getId();
        }
        return null;
    }

    @Override
    public Boolean hasUserChangedTempPW(Long userId) {
        Optional<MyUser> maybeUser = userRepository.findById(userId);
        if (maybeUser.isPresent()) {
            boolean hasChangedPWalready = maybeUser.get().isHasChangedPW();
            return hasChangedPWalready;
        }
        return null;
    }

    @Override
    public MyUserDto updatePassword(String mail, String newPw) {
        Optional<MyUser> maybeUser = userRepository.findByMail(mail);
        if (maybeUser.isPresent()) {
            MyUser user = maybeUser.get();
            user.setPassword(encoder.encode(newPw));
            user.setHasChangedPW(true);
            userRepository.save(user);
            return userMapper.toDto(user);
        }
        return null;
    }

    @Override
    public void getNewPassword(String mail) {
        Optional<MyUser> maybeUser = userRepository.findByMail(mail);

        if (maybeUser.isPresent()) {
            MyUser user = maybeUser.get();
            EmailDetails details = new EmailDetails(user.getMail(), user.getId());
            emailService.sendMailToResetPw(details);
            user.setHasChangedPW(false);
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no account connected to this mail");
        }
    }
}

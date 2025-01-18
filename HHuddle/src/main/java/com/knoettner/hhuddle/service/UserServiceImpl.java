package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.MyUserDto;
import com.knoettner.hhuddle.dto.mapper.MyUserMapper;
import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    MyUserMapper userMapper;

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
            userRepository.save(user);
            return userMapper.toDto(user);
        }
        return null;
    }
}

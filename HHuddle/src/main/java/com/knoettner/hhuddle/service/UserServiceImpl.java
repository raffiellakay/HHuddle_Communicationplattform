package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long getHouseIdByUser(Long userId) {
        Optional<MyUser> maybeUser = userRepository.findById(userId);
        if (maybeUser.isPresent()) {
            MyUser user = maybeUser.get();
            return user.getHouse().getId();
        }
        return 0L;
    }

    @Override
    public Boolean hasUserChangedFirstPW(Long userId) {
        Optional<MyUser> maybeUser = userRepository.findById(userId);
        if (maybeUser.isPresent()) {
            boolean hasChangedPWalready = maybeUser.get().isHasChangedPW();
            return hasChangedPWalready;
        }
        return null;
    }
}

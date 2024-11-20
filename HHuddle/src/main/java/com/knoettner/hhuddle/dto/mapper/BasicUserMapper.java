package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.dto.BasicUserDto;
import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BasicUserMapper {
    @Autowired
    UserRepository userRepository;

    public BasicUserDto toDto (MyUser user) {
        BasicUserDto basicUserDto = new BasicUserDto(user.getId(), user.getUsername());
        return basicUserDto;
    }

    public MyUser toEntity (BasicUserDto basicUserDto ) {
        MyUser user = new MyUser();
        Optional<MyUser> maybeUser = userRepository.findById(basicUserDto.getId());
        if (maybeUser.isPresent()) {
            MyUser realUser = maybeUser.get();

            return realUser;
        }
        else return null;


    }

}

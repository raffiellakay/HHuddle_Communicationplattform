package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.dto.MyUserDto;
import com.knoettner.hhuddle.models.*;
import com.knoettner.hhuddle.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserMapper {
@Autowired
RoleMapper roleMapper;

@Autowired
ChatMessageMapper chatMessageMapper;

@Autowired
UserPostMapper userPostMapper;

@Autowired
    HouseRepository houseRepository;

   public MyUserDto toDto (MyUser user) {
       MyUserDto userDto = new MyUserDto();
       userDto.setId(user.getId());
       userDto.setMail(user.getMail());
       userDto.setUsername(user.getUsername());
       userDto.setHouseId(user.getHouse().getId());
       return userDto;
   }

    public MyUser toEntity (MyUserDto userDto) {
        MyUser user = new MyUser();
        user.setId(userDto.getId());
        user.setMail(userDto.getMail());
        user.setUsername(userDto.getUsername());
        Optional<House> optionalHouse = houseRepository.findById(userDto.getHouseId());
        if (optionalHouse.isPresent()) {
            user.setHouse(optionalHouse.get());
        }

        return user;
    }


}

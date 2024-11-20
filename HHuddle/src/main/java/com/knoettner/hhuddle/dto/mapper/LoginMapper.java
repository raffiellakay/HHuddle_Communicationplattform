package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.dto.LoginDto;
import com.knoettner.hhuddle.dto.RoleDto;
import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.models.Role;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class LoginMapper {
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRepository userRepository;

    //notwendig?
    public LoginDto toDto (MyUser user) {
        LoginDto userLogin = new LoginDto();
        userLogin.setId(user.getId());
        userLogin.setPassword(user.getPassword());
        Set<RoleDto> roleDtoSet = new HashSet<>();
        for (Role currentRole : user.getRoles()) {
            RoleDto roleDto = roleMapper.toDto(currentRole);
            roleDtoSet.add(roleDto);
        }
        userLogin.setRoles(roleDtoSet);
        return userLogin;
    }

    public MyUser toEntity (LoginDto userLogin) {
        MyUser user = new MyUser();
        Optional<MyUser> maybeUser = userRepository.findById(userLogin.getId());
        if (maybeUser.isPresent()) {
            user = maybeUser.get();
        }
        return user;

    }

}

package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.dto.ChatMessageDto;
import com.knoettner.hhuddle.dto.MyUserDto;
import com.knoettner.hhuddle.dto.RoleDto;
import com.knoettner.hhuddle.dto.UserPostDto;
import com.knoettner.hhuddle.models.*;
import com.knoettner.hhuddle.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        userDto.setPassword(user.getPassword());
        userDto.setMail(user.getMail());
        userDto.setHouseId(user.getHouse().getId());
        if (user.getRoles() != null) {
            Set<RoleDto> roleDtoSet = new HashSet<>();
            for (Role currentRole : user.getRoles()) {
                RoleDto dto = roleMapper.toDto(currentRole);
                roleDtoSet.add(dto);
            }
            userDto.setRoles(roleDtoSet);
        }
        if (user.getMessages() != null) {
            Set<ChatMessageDto> messages = new HashSet<>();
            for (ChatMessage currentMessage : user.getMessages()) {
                ChatMessageDto dto = chatMessageMapper.toDto(currentMessage);
                messages.add(dto);
            }
            userDto.setMessages(messages);
        }
        if (user.getUserPosts() != null) {
            Set<UserPostDto> userPostDtos = new HashSet<>();
            for (UserPost currentPost : user.getUserPosts()) {
                UserPostDto dto = userPostMapper.toDto(currentPost);
                userPostDtos.add(dto);
            }
            userDto.setUserPosts(userPostDtos);
        }
    return userDto;
    }

    public MyUser toEntity (MyUserDto userDto) {
        MyUser user = new MyUser();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        user.setMail(userDto.getMail());
        user.setUsername(userDto.getUsername());

        if (userDto.getRoles() != null) {
            Set<Role> roles = new HashSet<>();
            for (RoleDto currentRole : userDto.getRoles()) {
                Role entity = roleMapper.toEntity(currentRole);
                roles.add(entity);
            }
            user.setRoles(roles);
        }
        if (userDto.getMessages() != null) {
            Set<ChatMessage> messages = new HashSet<>();
            for (ChatMessageDto currentMessage : userDto.getMessages()) {
                ChatMessage entity = chatMessageMapper.toEntity(currentMessage);
                messages.add(entity);
            }
            user.setMessages(messages);
        }
        if (userDto.getUserPosts() != null) {
            Set<UserPost> userPosts = new HashSet<>();
            for (UserPostDto currentUserPost : userDto.getUserPosts()) {
                UserPost entity = userPostMapper.toEntity(currentUserPost);
                userPosts.add(entity);

            }
            user.setUserPosts(userPosts);
        }
        Optional<House> optionalHouse = houseRepository.findById(userDto.getHouseId());
        if (optionalHouse.isPresent()) {
            user.setHouse(optionalHouse.get());
        }

        user.setFirst_participantInChat(new HashSet<>());
        user.setSecond_participantInChat(new HashSet<>());

        return user;
    }


}

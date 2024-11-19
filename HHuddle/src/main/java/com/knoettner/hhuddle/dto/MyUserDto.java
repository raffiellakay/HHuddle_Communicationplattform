package com.knoettner.hhuddle.dto;


import com.knoettner.hhuddle.models.ChatMessage;
import com.knoettner.hhuddle.models.ChatParticipants;
import com.knoettner.hhuddle.models.House;
import com.knoettner.hhuddle.models.UserPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.Set;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data

public class MyUserDto {
    private Long id;
    private String password;
    private String mail;
    private String username;
    private Set<Role>roles;
    private Set<ChatMessage> messages;
    private Set<ChatParticipants> first_participantInChat;
    private Set<ChatParticipants> second_participantInChat;
    private Set<UserPost> userPosts;
    private House house;


}

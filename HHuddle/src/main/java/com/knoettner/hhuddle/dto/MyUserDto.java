package com.knoettner.hhuddle.dto;


import com.knoettner.hhuddle.dto.mapper.UserPostDto;
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


@AllArgsConstructor
@NoArgsConstructor
@Data

public class MyUserDto {
    private Long id;
    private String password;
    private String mail;
    private String username;
    private Set<RoleDto>roles;
    private Set<ChatMessageDto> messages;
    //??private Set<ChatParticipantsDto> first_participantInChat;
    //??private Set<ChatParticipantsDto> second_participantInChat;
  // ??kein UserPost weil das über Post dann ins Repo vom user gepseicher twird?
  Set<UserPostDto> userPosts;
    private Long houseId;


}

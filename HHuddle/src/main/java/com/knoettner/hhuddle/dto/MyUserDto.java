package com.knoettner.hhuddle.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
//UserDto withouth Password!
public class MyUserDto {
    private Long id;
    private String mail;
    private String username;
    private Set<RoleDto>roles;
    // private Set<ChatMessageDto> messages;
    //??private Set<ChatParticipantsDto> first_participantInChat;
    //??private Set<ChatParticipantsDto> second_participantInChat;
  // ??kein UserPost weil das über Post dann ins Repo vom user gepseicher twird?
  Set<UserPostDto> userPosts;
    private Long houseId;


}

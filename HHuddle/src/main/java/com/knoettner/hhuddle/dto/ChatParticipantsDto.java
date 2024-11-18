package com.knoettner.hhuddle.dto;

import com.knoettner.hhuddle.models.MyUser;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data

public class ChatParticipantsDto {

    private int id;
    private LocalDateTime timestamp;
    private Set<ChatMessageDto> messages;
    private Set<ChatParticipantsDto> participants;


}

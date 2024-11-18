package com.knoettner.hhuddle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ChatDto {


    private int id;
    private LocalDateTime timestamp;
    private Set<ChatMessageDto> messages;
    private Set<ChatParticipantsDto> participants;


}

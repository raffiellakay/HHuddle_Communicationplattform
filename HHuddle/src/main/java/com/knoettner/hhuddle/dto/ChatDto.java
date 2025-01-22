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


    private Long id;
    private LocalDateTime timestamp;
    private BasicUserDto first_participant;
    private BasicUserDto second_participant;
    private Set<ChatMessageResponseDto> messages;
    private boolean visibleToFirstParticipant;
    private boolean visibleToSecondParticipant;


}

package com.knoettner.hhuddle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ChatMessageResponseDto {
    private Long id;
    private BasicUserDto user;
    private Long chatId;
    private LocalDateTime timestamp;
    private String text;
    private boolean isRead = false;


}

package com.knoettner.hhuddle.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ChatMessageRequestDto {
    private Long senderId;
    private Long chatId;
    private String text;

}

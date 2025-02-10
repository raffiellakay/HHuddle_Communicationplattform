package com.knoettner.hhuddle.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ChatMessageRequestDto { //unterscheidet sich von response denn der User braucht die Zeit nicht eingeben
    private Long senderId;
    private Long chatId;
    private String text;
    private boolean isRead = false;

}

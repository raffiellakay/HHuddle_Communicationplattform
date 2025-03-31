package com.knoettner.hhuddle.controller;

import com.knoettner.hhuddle.dto.ChatMessageRequestDto;
import com.knoettner.hhuddle.dto.mapper.ChatMessageMapper;
import com.knoettner.hhuddle.models.ChatMessage;
import com.knoettner.hhuddle.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller

public class ChatWebSocketController {

    @Autowired
    private  ChatService chatService;
    private  ChatMessageMapper chatMessageMapper;

    @Autowired
    public ChatWebSocketController(ChatMessageMapper chatMessageMapper, ChatService chatService) {
        this.chatMessageMapper = chatMessageMapper;
        this.chatService = chatService;
    }



    @MessageMapping("/chat.sendMessage") //Eigehender Pfad
    @SendTo("/topic/public") //Ausgehender Pfad
    public ChatMessage sendMessage( @Payload ChatMessageRequestDto chatMessageRequestDto) {
        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageRequestDto);

        return chatService.sendMessage(chatMessage);
    }
}
package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.dto.ChatDto;
import com.knoettner.hhuddle.dto.mapper.ChatMessageMapper;
import com.knoettner.hhuddle.dto.mapper.ChatParticipantsMapper;
import com.knoettner.hhuddle.models.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component

public class ChatMapper {
    @Autowired
    ChatMessageMapper chatMessageMapper;
    @Autowired
    ChatParticipantsMapper chatParticipantsMapper;


    public Chat toEntity(ChatDto chatDto) {
        Chat chat = new Chat();
        chat.setId(chatDto.getId());
        chat.setTimestamp(chatDto.getTimestamp());
        chat.setMessages(chatDto.getMessages().stream(). map(e ->chatMessageMapper.toEntity(e)).collect(Collectors.toSet()));
        chat.setParticipants(chatDto.getParticipants().stream().map(e ->chatParticipantsMapper.toEntity(e)).collect(Collectors.toSet()));
        return chat;
    }


    public ChatDto toDto(Chat chat) {
        ChatDto chatDto = new ChatDto();
        chatDto.setId(chat.getId());
        chatDto.setTimestamp(chat.getTimestamp());
        chatDto.setMessages(chat.getMessages().stream(). map(e ->chatMessageMapper.toDto(e)).collect(Collectors.toSet()));
        chatDto.setParticipants(chat.getParticipants().stream().map(e ->chatParticipantsMapper.toDto(e)).collect(Collectors.toSet()));
        return chatDto;
    }


}
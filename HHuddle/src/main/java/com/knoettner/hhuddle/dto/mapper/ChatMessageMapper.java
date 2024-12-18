package com.knoettner.hhuddle.dto.mapper;



import com.knoettner.hhuddle.dto.ChatMessageRequestDto;
import com.knoettner.hhuddle.dto.ChatMessageResponseDto;
import com.knoettner.hhuddle.models.Chat;
import com.knoettner.hhuddle.models.ChatMessage;
import com.knoettner.hhuddle.repository.ChatRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component

public class  ChatMessageMapper {
    @Autowired
    BasicUserMapper basicUserMapper;

    @Autowired
    ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;
/*
    public ChatMessage toEntity(ChatMessageResponseDto chatMessageResponseDto) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(chatMessageResponseDto.getId());
        chatMessage.setUser(basicUserMapper.toEntity(chatMessageResponseDto.getUser()));
        Optional<Chat> maybeChat = chatRepository.findById(chatMessageResponseDto.getChatId());
        if (maybeChat.isPresent()) {
            chatMessage.setChat(maybeChat.get());
        }
        chatMessage.setTimestamp(chatMessageResponseDto.getTimestamp());
        chatMessage.setText(chatMessageResponseDto.getText());

        return chatMessage;
    }
*/
    public ChatMessage toEntity(ChatMessageRequestDto chatMessageRequestDto) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(userRepository.findById(chatMessageRequestDto.getSenderId()).get());
        chatMessage.setChat(
                chatRepository.findById(
                        chatMessageRequestDto.getChatId()
                ).get()
        );
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setText(chatMessageRequestDto.getText());
        return chatMessage;
    }


    public ChatMessageResponseDto toDto(ChatMessage chatMessage) {
        ChatMessageResponseDto chatMessageResponseDto = new ChatMessageResponseDto();
        chatMessageResponseDto.setChatId(chatMessage.getChat().getId());
        chatMessageResponseDto.setId(chatMessage.getId());
        chatMessageResponseDto.setUser(basicUserMapper.toDto(chatMessage.getUser()));
        chatMessageResponseDto.setTimestamp(chatMessage.getTimestamp());
        chatMessageResponseDto.setText(chatMessage.getText());
        return chatMessageResponseDto;

    }
}

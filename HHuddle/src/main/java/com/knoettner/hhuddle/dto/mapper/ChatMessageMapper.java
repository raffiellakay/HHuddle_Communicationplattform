package com.knoettner.hhuddle.dto.mapper;



import com.knoettner.hhuddle.dto.ChatMessageDto;
import com.knoettner.hhuddle.models.Chat;
import com.knoettner.hhuddle.models.ChatMessage;
import com.knoettner.hhuddle.repository.ChatRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component

public class ChatMessageMapper {
    @Autowired
    BasicUserMapper basicUserMapper;

    @Autowired
    ChatRepository chatRepository;

    public ChatMessage toEntity(ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(chatMessageDto.getId());
        chatMessage.setUser(basicUserMapper.toEntity(chatMessageDto.getUser()));
        Optional<Chat> maybeChat = chatRepository.findById(chatMessageDto.getChatId());
        if (maybeChat.isPresent()) {
            chatMessage.setChat(maybeChat.get());
        }
        chatMessage.setTimestamp(chatMessageDto.getTimestamp());
        chatMessage.setText(chatMessageDto.getText());

        return chatMessage;
    }


    public ChatMessageDto toDto(ChatMessage chatMessage) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setId(chatMessage.getId());
        chatMessageDto.setUser(basicUserMapper.toDto(chatMessage.getUser()));
        chatMessageDto.setTimestamp(chatMessage.getTimestamp());
        chatMessageDto.setText(chatMessage.getText());
        return chatMessageDto;

    }
}

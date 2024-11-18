package com.knoettner.hhuddle.dto.mapper;



import com.knoettner.hhuddle.dto.ChatMessageDto;
import com.knoettner.hhuddle.models.ChatMessage;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component

public class ChatMessageMapper {

    public ChatMessage toEntity(ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(chatMessageDto.getId());
        chatMessage.setUser(chatMessageDto.getUser());
        chatMessage.setChat(chatMessageDto.getChat());
        chatMessage.setTimestamp(chatMessageDto.getTimestamp());
        chatMessage.setText(chatMessageDto.getText());

        return chatMessage;
    }


    public ChatMessageDto toDto(ChatMessage chatMessage) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setId(chatMessage.getId());
        chatMessageDto.setUser(chatMessage.getUser());
        chatMessageDto.setChat(chatMessage.getChat());
        chatMessageDto.setTimestamp(chatMessage.getTimestamp());
        chatMessageDto.setText(chatMessage.getText());
        return chatMessageDto;

    }
}

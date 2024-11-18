

package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.dto.ChatParticipantsDto;
import com.knoettner.hhuddle.models.ChatParticipants;
import com.knoettner.hhuddle.models.Role;
import org.springframework.stereotype.Component;

@Component
public class ChatParticipantsMapper {

    public ChatParticipants toEntity(ChatParticipantsDto chatParticipantsDto) {
        ChatParticipants chatParticipants = new ChatParticipants();

        // Set the composite key (ChatParticipantKey)
        Role.ChatParticipantKey key = new Role.ChatParticipantKey();
        key.setFirstUserId(chatParticipantsDto.getFirstUserId());
        key.setSecondUserId(chatParticipantsDto.getSecondUserId());
        chatParticipants.setId(key);

        // Set other fields
        chatParticipants.setFirstUser(chatParticipantsDto.getFirstUser());
        chatParticipants.setSecondUser(chatParticipantsDto.getSecondUser());
        chatParticipants.setChat(chatParticipantsDto.getChat());

        return chatParticipants;
    }

    public ChatParticipantsDto toDto(ChatParticipants chatParticipants) {
        ChatParticipantsDto chatParticipantsDto = new ChatParticipantsDto();

        // Extract the composite key
        Role.ChatParticipantKey key = chatParticipants.getId();
        if (key != null) {
            chatParticipantsDto.setFirstUserId(key.getFirstUserId());
            chatParticipantsDto.setSecondUserId(key.getSecondUserId());
        }

        // Set other fields
        chatParticipantsDto.setFirstUser(chatParticipants.getFirstUser());
        chatParticipantsDto.setSecondUser(chatParticipants.getSecondUser());
        chatParticipantsDto.setChat(chatParticipants.getChat());

        return chatParticipantsDto;
    }
}



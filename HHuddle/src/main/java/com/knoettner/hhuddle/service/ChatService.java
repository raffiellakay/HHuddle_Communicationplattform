package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.ChatDto;
import com.knoettner.hhuddle.dto.ChatMessageRequestDto;
import com.knoettner.hhuddle.dto.ChatMessageResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatService {



    @Transactional
    ChatDto createChat(Long firstUserId, Long secondUserId, String text);

    ChatMessageResponseDto sendMessage(ChatMessageRequestDto chatMessageRequestDto);

   // void deleteChat(Long id);

    void deleteChatForUser(Long chatId, Long userId);

    ChatDto getChatById(Long chatId);

    List<ChatDto> getChatsByUserId(Long userId);

}

package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.ChatDto;
import com.knoettner.hhuddle.dto.ChatMessageRequestDto;
import com.knoettner.hhuddle.dto.ChatMessageResponseDto;

import java.util.List;

public interface ChatService {


    ChatDto createChat(Long firstUserId, Long secondUserId);

    ChatMessageResponseDto sendMessage(ChatMessageRequestDto chatMessageRequestDto);

   // void deleteChat(Long id);

    ChatDto getChatById(Long chatId);

    List<ChatDto> getChatsByUserId(Long userId);

}

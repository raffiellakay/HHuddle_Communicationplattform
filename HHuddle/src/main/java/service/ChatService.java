package service;

import com.knoettner.hhuddle.dto.ChatDto;
import com.knoettner.hhuddle.dto.ChatMessageDto;

import java.util.List;

public interface ChatService {


    ChatDto createChat(Long firstUserId, Long secondUserId);

    ChatMessageDto sendMessage(Long chatId, ChatMessageDto chatMessageDto);

    void deleteChat(Long id);

    ChatDto getChatById(Long chatId);

    List<ChatDto> getChatsByUserId(Long userId);

}

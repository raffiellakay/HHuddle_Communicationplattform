package service;

import com.knoettner.hhuddle.dto.ChatDto;
import com.knoettner.hhuddle.dto.ChatMessageDto;
import com.knoettner.hhuddle.dto.mapper.ChatMapper;
import com.knoettner.hhuddle.dto.mapper.ChatMessageMapper;
import com.knoettner.hhuddle.models.*;
import com.knoettner.hhuddle.repository.ChatRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Transactional
    @Override
    public ChatDto createChat(Long firstUserId, Long secondUserId) {
        MyUser firstUser = userRepository.findById(firstUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "First user not found"));
        MyUser secondUser = userRepository.findById(secondUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Second user not found"));

        // Create a new chat
        Chat chat = new Chat();
        chat.setTimestamp(LocalDateTime.now());
        chat.setParticipants(new HashSet<>()); // Sicherstellen, dass die Liste initialisiert ist

        // Create ChatParticipants
        ChatParticipantKey participantKey = new ChatParticipantKey(firstUserId, secondUserId);
        ChatParticipants participants = new ChatParticipants();
        participants.setId(participantKey);
        participants.setFirstUser(firstUser);
        participants.setSecondUser(secondUser);
        participants.setChat(chat);

        // Add participants to chat
        chat.getParticipants().add(participants);

        // Save the chat
        Chat savedChat = chatRepository.save(chat);
        return chatMapper.toDto(savedChat);
    }

    @Transactional
    @Override
    public ChatMessageDto sendMessage(Long chatId, ChatMessageDto chatMessageDto) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found"));

        MyUser sender = userRepository.findById(chatMessageDto.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));

        // Validate if sender is part of the chat
        boolean isParticipant = chat.getParticipants().stream()
                .anyMatch(p -> p.getFirstUser().getId().equals(sender.getId()) ||
                        p.getSecondUser().getId().equals(sender.getId()));
        if (!isParticipant) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not a participant of the chat");
        }

        // Create and add a new message
        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageDto);
        chatMessage.setChat(chat);
        chatMessage.setUser(sender);
        chatMessage.setTimestamp(LocalDateTime.now());

        chat.getMessages().add(chatMessage);
        chatRepository.save(chat);

        return chatMessageMapper.toDto(chatMessage);
    }

    @Override
    public void deleteChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found"));
        chatRepository.delete(chat);
    }

    @Override
    public ChatDto getChatById(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found"));
        return chatMapper.toDto(chat);
    }

    @Override
    public List<ChatDto> getChatsByUserId(Long userId) {
        MyUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<Chat> chats = chatRepository.findAllByParticipants(user);
        return chats.stream()
                .map(chatMapper::toDto)
                .collect(Collectors.toList());
    }
}

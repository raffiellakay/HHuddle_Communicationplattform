package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.ChatDto;
import com.knoettner.hhuddle.dto.ChatMessageRequestDto;
import com.knoettner.hhuddle.dto.ChatMessageResponseDto;
import com.knoettner.hhuddle.dto.mapper.ChatMapper;
import com.knoettner.hhuddle.dto.mapper.ChatMessageMapper;
import com.knoettner.hhuddle.models.*;
import com.knoettner.hhuddle.repository.ChatMessageRepository;
import com.knoettner.hhuddle.repository.ChatRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

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



       List list = chatRepository.findAllByFirstParticipantAndSecondParticipant( //suchen die Chats nach dem Participant
               firstUserId>secondUserId?secondUser:firstUser,
               firstUserId>secondUserId?firstUser:secondUser //User nach der Id ordnen;

       );
       if(firstUserId.equals(secondUserId))
           throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User can´t chat with oneself");

        if(!list.isEmpty())
            throw new ResponseStatusException(HttpStatus.FOUND, "the chat has been already created");

        // Create a new chat
        Chat chat = new Chat();
        chat.setTimestamp(LocalDateTime.now());


        chat.setFirstParticipant(firstUserId>secondUserId?secondUser:firstUser);
        chat.setSecondParticipant(firstUserId>secondUserId?firstUser:secondUser);
        chat.setVisibleToFirstParticipant(true); //  visibility for the first participant
        chat.setVisibleToSecondParticipant(true); //  visibility for the second participant



        // Save the chat
        Chat savedChat = chatRepository.save(chat);
        return chatMapper.toDto(savedChat);
    }

    @Transactional
    @Override
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto chatMessageRequestDto) {
        Chat chat = chatRepository.findById(chatMessageRequestDto.getChatId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found"));

        MyUser sender = userRepository.findById(chatMessageRequestDto.getSenderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found"));

        // Validate if sender is a part of the chat
        boolean isParticipant =
                        chat.getFirstParticipant().getId()  == sender.getId()||
                        chat.getSecondParticipant().getId() == sender.getId();

        if (!isParticipant) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not a participant of the chat");
        }

        // Create and add a new message
        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageRequestDto);

        chatMessageRepository.save(chatMessage);

        List<ChatMessage> sortedMessages = chatMessageRepository.findAllMessagesByChatId(chatMessageRequestDto.getChatId());


        return chatMessageMapper.toDto(chatMessage);
    }

   /* @Override
    public void deleteChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found"));
                Set<ChatMessage> messages = chat.getMessages();
                for (ChatMessage currentMessage : messages) {
                    chatMessageRepository.deleteById(currentMessage.getId()); //TODO einseitig chat nicht sichtbar macht
                }
        chatRepository.delete(chat);
    }*/
@Transactional
    @Override
    public void deleteChatForUser(Long chatId, Long userId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found"));
        if (!chat.getFirstParticipant().getId().equals(userId) && !chat.getSecondParticipant().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not a participant of the chat");
        }
      /*  if (chat.getFirstParticipant().getId().equals(userId)) {
        } else if (chat.getSecondParticipant().getId().equals(userId)) {
            chat.setVisibleToSecondParticipant(false);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not a participant of the chat");
        }

        // delete chat if both have deleted
        if (!chat.isVisibleToFirstParticipant() && !chat.isVisibleToSecondParticipant()) {*/

        // Delete messages associated with this chat first
        chatMessageRepository.deleteByChatId(chatId);

        // Now delete the chat
        chatRepository.deleteById(chatId);

     /*   } else {
            chatRepository.save(chat);
        }*/
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

        List<Chat> chats = chatRepository.findAllByFirstParticipantOrSecondParticipant(user, user);
        return chats.stream()
                .map(chatMapper::toDto)
                .collect(Collectors.toList());
    }

    //to delete Chats when user is updated

    @Override
    public void deleteChatById(Long chatId) {
        Optional<Chat> maybeChat = chatRepository.findById(chatId);
        if(maybeChat.isPresent()) {
            chatRepository.deleteById(chatId);
        }
        if(maybeChat.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found");
        }
    }

    @Transactional
    @Override
    public void markChatAsRead(Long chatId) {
        // 1) Check that chat exists
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found"));

        // 2) Fetch all messages for this chat
        List<ChatMessage> messages = chatMessageRepository.findAllMessagesByChatId(chatId);

        // 3) Set isRead = true for each message
        for (ChatMessage msg : messages) {
            msg.setRead(true);
        }

        // 4) Save updated messages
        chatMessageRepository.saveAll(messages);
    }
}

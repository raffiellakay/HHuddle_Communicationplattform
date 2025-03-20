package com.knoettner.hhuddle.controller;

import com.knoettner.hhuddle.dto.ChatDto;
import com.knoettner.hhuddle.dto.ChatMessageRequestDto;
import com.knoettner.hhuddle.dto.ChatMessageResponseDto;
import com.knoettner.hhuddle.models.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.hibernate.PessimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.knoettner.hhuddle.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor //Lombok automatically generates a constructor with required dependencies



public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatService chatService;


    //Create a new Chat
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<ChatDto> createChat(
            @RequestParam Long firstUserId,
            @RequestParam Long secondUserId) {
        ChatDto createdChat = chatService.createChat(firstUserId, secondUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChat);
    }

    //@ResponseStatus(HttpStatus.OK)
   // @PostMapping("/send-message")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('RESIDENT')")
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")//broadcast to all users
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    // Einzelnen Chat abrufen
    @GetMapping("/{chatId}")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<ChatDto> getChat(@PathVariable Long chatId) {
        ChatDto chatDto = chatService.getChatById(chatId);
        return ResponseEntity.ok(chatDto);
    }

     //Chat (für User) löschen
    @DeleteMapping("/{chatId}")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<Void> deleteChat(@PathVariable Long chatId, Long userId) {
        chatService.deleteChatForUser(chatId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<List<ChatDto>> getUserChats(@PathVariable Long userId) {
        List<ChatDto> chats = chatService.getChatsByUserId(userId);
        return ResponseEntity.ok(chats);
    }

    @PutMapping("/{chatId}/markRead")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('RESIDENT')")
    public void markChatAsRead(@PathVariable Long chatId) {
        chatService.markChatAsRead(chatId);
    }



}
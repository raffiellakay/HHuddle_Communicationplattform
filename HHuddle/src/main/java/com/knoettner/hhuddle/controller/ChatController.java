package com.knoettner.hhuddle.controller;

import com.knoettner.hhuddle.dto.ChatDto;
import com.knoettner.hhuddle.dto.ChatMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.knoettner.hhuddle.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chats")

public class ChatController {

    @Autowired
    private ChatService chatService;

    //Create a new Chat
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<ChatDto> createChat(
            @RequestParam Long firstUserId,
            @RequestParam Long secondUserId) {
        ChatDto createdChat = chatService.createChat(firstUserId, secondUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChat);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{chatId}/send-message")
    public ResponseEntity<ChatMessageDto> sendMessage(
            @PathVariable Long chatId,
            @RequestBody ChatMessageDto chatMessageDto) {
        ChatMessageDto sentMessage = chatService.sendMessage(chatId, chatMessageDto);
        return ResponseEntity.ok(sentMessage);
    }

    // Einzelnen Chat abrufen
    @GetMapping("/{chatId}")
    public ResponseEntity<ChatDto> getChat(@PathVariable Long chatId) {
        ChatDto chatDto = chatService.getChatById(chatId);
        return ResponseEntity.ok(chatDto);
    }

    // 5. Chat löschen
    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatDto>> getUserChats(@PathVariable Long userId) {
        List<ChatDto> chats = chatService.getChatsByUserId(userId);
        return ResponseEntity.ok(chats);
    }

}
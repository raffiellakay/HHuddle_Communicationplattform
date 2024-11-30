package com.knoettner.hhuddle.controller;

import com.knoettner.hhuddle.dto.ChatDto;
import com.knoettner.hhuddle.dto.ChatMessageRequestDto;
import com.knoettner.hhuddle.dto.ChatMessageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.knoettner.hhuddle.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@Tag(name = "Demo API", description = "API für Testzwecke")

public class ChatController {


    @Autowired
    private ChatService chatService;

    @Operation(summary = "Gibt eine Willkommensnachricht zurück")
    @GetMapping("/welcome")
    @ResponseStatus(HttpStatus.OK)
    public String getWelcomeMessage() {
        return "Willkommen in der Demo-API!";
    }
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
    @PostMapping("/send-message")
    public ResponseEntity<ChatMessageResponseDto> sendMessage(
            @RequestBody ChatMessageRequestDto chatMessageRequestDto) {
        ChatMessageResponseDto sentMessage = chatService.sendMessage(chatMessageRequestDto);
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
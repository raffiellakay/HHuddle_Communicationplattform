package com.knoettner.hhuddle;

import com.knoettner.hhuddle.dto.ChatDto;
import com.knoettner.hhuddle.models.MyUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class  ChatTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Test
    void createChat() {

        ChatDto chatDto = new ChatDto();
        chatDto.setParticipants(new HashSet<>()); // Initialize participants

        ResponseEntity<ChatDto> response = testRestTemplate.postForEntity("/api/chats", chatDto, ChatDto.class);
        Long chatId = response.getBody().getId();




    }


}

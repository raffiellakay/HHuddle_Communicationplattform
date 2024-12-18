package com.knoettner.hhuddle;

import com.knoettner.hhuddle.dto.BasicUserDto;
import com.knoettner.hhuddle.dto.ChatDto;
import com.knoettner.hhuddle.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChatTest {
    @Autowired
    private ChatService chatService;

    @Test
    public void testCreateChat() {

    }
}

package com.knoettner.hhuddle;

import com.knoettner.hhuddle.dto.ChatDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatTester {
    @Autowired
    TestRestTemplate testRestTemplate;


    @Test
    void testCreateChat() {

        Long firstUserId = 1L;
        Long secondUserId = 2L;

        ResponseEntity<ChatDto> response = testRestTemplate.postForEntity(
                "/api/chat/create?firstUserId=" + firstUserId + "&secondUserId=" + secondUserId,
                null, ChatDto.class
        );


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirst_participant()).isNotNull();
        assertThat(response.getBody().getSecond_participant()).isNotNull();
        assertThat(response.getBody().getFirst_participant().getId()).isEqualTo(firstUserId);
        assertThat(response.getBody().getSecond_participant().getId()).isEqualTo(secondUserId);
    }


    }



package com.knoettner.hhuddle;

import com.knoettner.hhuddle.dto.BasicUserDto;
import com.knoettner.hhuddle.dto.ChatDto;
import com.knoettner.hhuddle.models.Chat;
import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.repository.ChatRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import com.knoettner.hhuddle.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.HashSet;

@SpringBootApplication
public class HHuddleApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

    public static void main(String[] args) {
        SpringApplication.run(HHuddleApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        // Create participants
        BasicUserDto participant1 = new BasicUserDto();
        participant1.setId(1L);
        participant1.setUsername("user1");

        BasicUserDto participant2 = new BasicUserDto();
        participant2.setId(2L);
        participant2.setUsername("user2");

        //Create Chat
        ChatDto chatDto = new ChatDto();
        chatDto.setTimestamp(LocalDateTime.now());
        chatDto.setFirst_participant(participant1);
        chatDto.setSecond_participant(participant2);
        chatDto.setMessages(new HashSet<>());


        /*MyUser user = new MyUser(1L, "kkkk", "raffy", "TOP2", null, null, null, null, null, null);
        userRepository.save(user);*/




    }
}

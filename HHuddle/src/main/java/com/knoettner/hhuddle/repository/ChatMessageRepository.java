package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.Chat;
import com.knoettner.hhuddle.models.ChatMessage;
import com.knoettner.hhuddle.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByTimestamp(LocalDateTime timestamp);
}

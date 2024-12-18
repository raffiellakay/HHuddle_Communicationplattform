package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.Chat;
import com.knoettner.hhuddle.models.ChatMessage;
import com.knoettner.hhuddle.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByTimestamp(LocalDateTime timestamp);
}

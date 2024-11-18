package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}

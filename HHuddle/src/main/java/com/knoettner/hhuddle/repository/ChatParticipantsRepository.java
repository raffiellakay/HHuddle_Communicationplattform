package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.ChatParticipants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatParticipantsRepository extends JpaRepository<ChatParticipants, Long> {
}

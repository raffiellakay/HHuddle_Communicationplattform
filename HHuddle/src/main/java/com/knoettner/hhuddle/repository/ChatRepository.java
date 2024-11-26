package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.Chat;
import com.knoettner.hhuddle.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByFirstParticipantAndSecondParticipant(MyUser firstUser, MyUser secondUser);
    List<Chat> findAllByFirstParticipantOrSecondParticipant(MyUser firstUser, MyUser secondUser);
}

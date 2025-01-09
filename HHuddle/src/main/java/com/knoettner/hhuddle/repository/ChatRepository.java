package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.Chat;
import com.knoettner.hhuddle.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByFirstParticipantAndSecondParticipant(MyUser firstUser, MyUser secondUser);
    List<Chat> findAllByFirstParticipantOrSecondParticipant(MyUser firstUser, MyUser secondUser);
    @Query("SELECT c FROM Chat c WHERE " +
            "(c.firstParticipant.id = :userId AND c.visibleToFirstParticipant = true) OR " +
            "(c.secondParticipant.id = :userId AND c.visibleToSecondParticipant = true)")
    List<Chat> findVisibleChatsByUserId(@Param("userId") Long userId);

}

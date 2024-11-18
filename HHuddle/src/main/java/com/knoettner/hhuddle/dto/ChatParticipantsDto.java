package com.knoettner.hhuddle.dto;

import com.knoettner.hhuddle.models.Chat;
import com.knoettner.hhuddle.models.MyUser;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data

public class ChatParticipantsDto {


        private Long firstUserId;  // Korrespondiert mit ChatParticipantKey.firstUserId
        private Long secondUserId; // Korrespondiert mit ChatParticipantKey.secondUserId
        private Long chatId;       // Korrespondiert mit Chat.chat.id

        private MyUser firstUser;  // Referenz auf das erste Benutzerobjekt
        private MyUser secondUser; // Referenz auf das zweite Benutzerobjekt
        private Chat chat;
}


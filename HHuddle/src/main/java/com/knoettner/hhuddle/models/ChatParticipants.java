package com.knoettner.hhuddle.models;


import com.knoettner.hhuddle.ChatParticipantKey;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class ChatParticipants {
    @EmbeddedId
    ChatParticipantKey id;

    @ManyToOne
    @MapsId("firstUserId")
    MyUser firstUser;

    @ManyToOne
    @MapsId("secondUserId")
    MyUser secondUser;

    @OneToOne
    @JoinColumn (name = "chat_id_FK")
    Chat chat;


}

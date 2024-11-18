package com.knoettner.hhuddle.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    @ManyToOne
    @JoinColumn (name = "chat_id_FK")
    Chat chat;


}

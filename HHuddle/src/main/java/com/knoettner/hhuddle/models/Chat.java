package com.knoettner.hhuddle.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="chat_id")
    private int id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "chat")
    Set<ChatMessage> messages;

    @OneToMany(mappedBy = "chat")
    Set<ChatParticipants> participants;
}

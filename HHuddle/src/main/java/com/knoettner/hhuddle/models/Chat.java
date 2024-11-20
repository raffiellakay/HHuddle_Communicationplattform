package com.knoettner.hhuddle.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="chat_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "chat")
    Set<ChatMessage> messages;

    @OneToMany(mappedBy = "chat")
    Set<ChatParticipants> participants;
}

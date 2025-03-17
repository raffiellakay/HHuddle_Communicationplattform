package com.knoettner.hhuddle.models;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ChatMessage {


   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   //generated ID is necessary in almost all models with DTOs
   private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id_FK")
    private MyUser user;

    @ManyToOne
    @JoinColumn(name = "chat_id_FK")
    private Chat chat ;

    @Column(nullable = false)
    private LocalDateTime timestamp;
    @Column(nullable = false, length=1000000)
    private String text;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    private MessageType type;



}

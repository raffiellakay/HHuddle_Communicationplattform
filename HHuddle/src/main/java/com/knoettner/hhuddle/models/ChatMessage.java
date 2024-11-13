package com.knoettner.hhuddle.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatMessage {


   @Id
   private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id_FK")
    private MyUser user;

    @ManyToOne
    @JoinColumn(name = "chat_id_FK")
    private Chat chat ;

    @Column(nullable = false)
    private LocalDateTime timestamp;
    @Column(nullable = false)
    private String text;

}

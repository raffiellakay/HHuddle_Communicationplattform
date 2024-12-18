package com.knoettner.hhuddle.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;


@Setter
@Getter
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

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    Set<ChatMessage> messages;

   @ManyToOne
   @JoinColumn( name = "first_participant_id")
    private MyUser firstParticipant;

    @ManyToOne
    @JoinColumn( name = "second_participant_id")
    private MyUser secondParticipant;



}

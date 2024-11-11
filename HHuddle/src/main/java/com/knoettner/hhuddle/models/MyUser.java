package com.knoettner.hhuddle.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    //wie encodieren?
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String mail;
    @Column(nullable = false)
    private String username;

    @ManyToMany
    @JoinTable(
            name = "userRole",
            joinColumns = @JoinColumn(name = "user_id_FK"),
            inverseJoinColumns = @JoinColumn(name = "role_id_FK")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    Set<ChatMessage> messages;

    @OneToMany(mappedBy =  "firstUser")
    Set<ChatParticipants> first_participantInChat;

    @OneToMany (mappedBy = "secondUser")
    Set<ChatParticipants> second_participantInChat;
}

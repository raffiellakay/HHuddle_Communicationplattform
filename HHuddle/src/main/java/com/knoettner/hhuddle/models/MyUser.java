package com.knoettner.hhuddle.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id_FK"),
            inverseJoinColumns = @JoinColumn(name = "role_id_FK")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    Set<ChatMessage> messages;

    //unklar ob chatParticipants so stimmt
    @OneToMany(mappedBy =  "firstParticipant")
    Set<Chat> first_participantInChat;// nach dem User schauen in welchen Chats er ist

    @OneToMany (mappedBy = "secondParticipant")
    Set<Chat> second_participantInChat;

    @OneToMany(mappedBy =  "user")
    Set<UserPost> userPosts;


    @ManyToOne
    @JoinColumn( name = "house_id_FK")
    private House house;
}

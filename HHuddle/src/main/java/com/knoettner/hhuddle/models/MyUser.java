package com.knoettner.hhuddle.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    Set<ChatMessage> messages;

    @OneToMany(mappedBy =  "firstParticipant", fetch = FetchType.EAGER)
    Set<Chat> first_participantInChat;// nach dem User schauen in welchen Chats er ist

    @OneToMany (mappedBy = "secondParticipant", fetch = FetchType.EAGER)
    Set<Chat> second_participantInChat;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    Set<UserPost> userPosts;


    @ManyToOne
    @JoinColumn( name = "house_id_FK")
    private House house;

    private boolean hasChangedPW = false;


}

package com.knoettner.hhuddle.models;

import com.knoettner.hhuddle.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Entity
public class Role {
    @Id
    private int id;
    @Column( nullable = false)
    private UserRole type;
    @ManyToMany(mappedBy = "roles")
    private Set<MyUser> Users;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode

    @Embeddable
    public static class ChatParticipantKey implements Serializable {
        @Column(name = "first_userId")
        Long firstUserId;

        @Column (name = "second_userId")
        Long secondUserId;

    }
}

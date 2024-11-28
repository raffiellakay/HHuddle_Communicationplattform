package com.knoettner.hhuddle.models;

import com.knoettner.hhuddle.UserPostKey;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class UserPost {
    @EmbeddedId
    UserPostKey id;

    @ManyToOne
    @MapsId("userId")
    MyUser user;

    @OneToOne
    @MapsId("postId")
    Post post;

    @ManyToOne
    @MapsId("boardId")
    Board board;
}

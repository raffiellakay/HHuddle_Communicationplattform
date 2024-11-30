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
    @MapsId("boardId")
    @JoinColumn(name = "board_id_fk")
    Board board;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id_fk")
    MyUser user;

    @OneToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id_fk")
    Post post;
}

package com.knoettner.hhuddle.models;

import com.knoettner.hhuddle.UserPostKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class UserPost {
    @EmbeddedId
    UserPostKey id;

    @ManyToOne
    @MapsId("userId")
    MyUser user;

    @ManyToOne
    @MapsId("postId")
    Post post;

    @ManyToOne
    @MapsId("boardId")
    Board board;
}

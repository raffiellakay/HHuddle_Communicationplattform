package com.knoettner.hhuddle;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Embeddable
public class UserPostKey implements Serializable {
    @Column (name = "board_id_fk")
    Long boardId;

    @Column(name = "user_id_fk")
    Long userId;

    @Column (name = "post_id_fk")
    Long postId;
}

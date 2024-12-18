package com.knoettner.hhuddle.dto;

import com.knoettner.hhuddle.UserPostKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDto {
    private Long userId;
    private Long postId;
    private Long boardId;
}

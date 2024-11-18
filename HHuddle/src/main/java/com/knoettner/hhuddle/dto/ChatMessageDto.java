package com.knoettner.hhuddle.dto;

import com.knoettner.hhuddle.models.Chat;
import com.knoettner.hhuddle.models.MyUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ChatMessageDto {
    private Long id;
    private MyUser user;
    private Chat chat ;
    private LocalDateTime timestamp;
    private String text;


}

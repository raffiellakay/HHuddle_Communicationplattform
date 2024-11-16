package com.knoettner.hhuddle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {

    private Long id;
    private String password;
    private String mail;
    private String username;
    private Long houseId;
}

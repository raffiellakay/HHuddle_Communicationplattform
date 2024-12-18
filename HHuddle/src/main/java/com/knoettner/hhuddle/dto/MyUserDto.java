package com.knoettner.hhuddle.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
//UserDto withouth Password!
public class MyUserDto {
    private Long id;
    private String mail;
    private String username;
    private Long houseId;


}

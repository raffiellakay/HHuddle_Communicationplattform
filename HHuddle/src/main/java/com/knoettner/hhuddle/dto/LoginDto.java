package com.knoettner.hhuddle.dto;

import lombok.*;

import java.util.Set;
//No Mapper necessary, search for right user in service implemented
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String password;
    private String username;
}

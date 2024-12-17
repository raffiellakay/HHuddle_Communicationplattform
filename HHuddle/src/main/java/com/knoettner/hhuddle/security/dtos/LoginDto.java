package com.knoettner.hhuddle.security.dtos;

import lombok.*;

//No Mapper necessary, search for right user in service implemented
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String mail;
    private String password;

}

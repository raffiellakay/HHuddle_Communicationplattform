package com.knoettner.hhuddle.security.modelsDtos;

import lombok.*;

//No Mapper necessary, search for right user in service implemented
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//what backend needs from frontend to login user
public class LoginDto {
    private String mail;
    private String password;

}

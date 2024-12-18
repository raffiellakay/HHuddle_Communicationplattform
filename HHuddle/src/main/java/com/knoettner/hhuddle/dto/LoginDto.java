package com.knoettner.hhuddle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
//No Mapper necessary, search for right user in service implemented
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String password;
    private String username;
}

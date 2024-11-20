package com.knoettner.hhuddle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private Long id;
    private String password;
    private String username;
    private Set<RoleDto> roles;

}

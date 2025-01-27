package com.knoettner.hhuddle.dto;

import com.knoettner.hhuddle.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//Necessary for Creating Admins - DTO w PW + other necessary fields
public class CreateAdminDto {
    private Long id;
    private String password;
    private String mail;
    private String username;
    private Set<Role> roles;
    //private Long houseId;
    private boolean hasChangedPW = true;
}

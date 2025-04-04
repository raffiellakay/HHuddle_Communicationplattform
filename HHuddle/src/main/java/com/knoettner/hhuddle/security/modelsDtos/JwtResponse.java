package com.knoettner.hhuddle.security.modelsDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String mail;
    private List<String> roles;
    private Long houseId;
    //what backend responses when login method is called
    public JwtResponse(String token,  Long id, String username, String mail, List<String> roles, Long houseId) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.roles = roles;
        this.houseId = houseId;
    }
}


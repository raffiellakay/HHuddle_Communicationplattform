package com.knoettner.hhuddle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

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

    public JwtResponse(String token,  Long id, String username, String mail, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.roles = roles;
    }
}


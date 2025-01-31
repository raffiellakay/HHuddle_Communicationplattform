package com.knoettner.hhuddle.security.modelsDtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactFormDetails {

    private String firstName;
    private String lastName;
    private String mail;
    private String message;
}

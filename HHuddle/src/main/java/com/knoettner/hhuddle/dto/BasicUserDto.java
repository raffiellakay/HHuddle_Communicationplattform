package com.knoettner.hhuddle.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicUserDto {
    // only includes username and id (for postdto)

    private Long id;
    private String username;
}

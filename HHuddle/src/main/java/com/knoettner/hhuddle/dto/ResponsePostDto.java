package com.knoettner.hhuddle.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResponsePostDto {

    private Long id;
    private String title;
    private String text;
    private BoardDto board; //category deleted


    //optionale Felder

    private byte[] photo;
    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private boolean isPrivate;

    private FacilityDto facility;



    //BasicUserDto --> only necessary Info to Frontend!
    private BasicUserDto user;
    private Long boardId;
}

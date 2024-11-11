package com.knoettner.hhuddle.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor


@Entity
public class Post {
    @Id
    private Long id;
    @Column(name = "post_title",nullable = false)
    private String title;
    private String text;
    //enum category category
    @Column(nullable = false)
    private LocalDateTime timestamp;

    //optionale Felder
    private boolean isAnonymous;
    // wie mit datentyp blob umgehen? ist eig anderer Datentyp?
    // private Blob photo;
    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private boolean isPrivate;

    //fk facility?
    //beziehung userpost
}

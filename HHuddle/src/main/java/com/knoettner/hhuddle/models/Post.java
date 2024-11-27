package com.knoettner.hhuddle.models;


import com.knoettner.hhuddle.Category;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


@Entity
public class Post {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "post_title",nullable = false)
    private String title;
    private String text;
    private Category category;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @OneToOne(mappedBy =  "post")
    private UserPost userPost;
    //optionale Felder

    private boolean isAnonymous;

    private java.sql.Blob photo;

    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private boolean isPrivate;

    @ManyToOne
    private Facility facility;


}

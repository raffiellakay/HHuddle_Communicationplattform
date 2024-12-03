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
    @ManyToOne
    private Board board;//instead of category. Post bezieht sich auf Board. Das Feld Category kommt mit Board
    @OneToOne(mappedBy =  "post", fetch = FetchType.EAGER)
    private UserPost userPost;
    //optionale Felder

    private boolean isAnonymous;

    private String pathToPhoto;

    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private boolean isPrivate;

    @ManyToOne
    private Facility facility;


}

package com.knoettner.hhuddle.models;


import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(length=1000000)
    private String text;
    private Category category;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @OneToOne(mappedBy =  "post", fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)// cascade to delete from both tables
    private UserPost userPost;
    //optionale Felder

    private boolean isAnonymous;

    private String pathToImage;

    private LocalDateTime starttime;
    private LocalDateTime endtime;

    @JsonProperty("isPrivate")
    private boolean isPrivate;

    @ManyToOne
    private Facility facility;


}

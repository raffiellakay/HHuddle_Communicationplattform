package com.knoettner.hhuddle.models;


import com.knoettner.hhuddle.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Set;

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
    private Category category;
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

    @OneToMany(mappedBy =  "post")
    Set<UserPost> userPosts;
}

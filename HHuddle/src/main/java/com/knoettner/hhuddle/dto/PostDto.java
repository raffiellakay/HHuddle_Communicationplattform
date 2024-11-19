package com.knoettner.hhuddle.dto;


import com.knoettner.hhuddle.Category;
import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.models.Facility;
import com.knoettner.hhuddle.models.UserPost;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PostDto {

    private Long id;
    private String title;
    private String text;
    private Category category;
    private LocalDateTime timestamp;

    //optionale Felder

    private boolean isAnonymous;
    private java.sql.Blob photo;
    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private boolean isPrivate;

    private Long facilityId;
// da zusammengesetzer Primary Key
    private UserPostKey userPostId;
}

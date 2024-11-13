package com.knoettner.hhuddle.models;

import com.knoettner.hhuddle.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Board {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn( name = "house_id_FK")
    private House house;

    @OneToMany(mappedBy =  "board")
    Set<UserPost> userPosts;


}

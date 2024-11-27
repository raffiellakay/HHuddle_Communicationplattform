package com.knoettner.hhuddle.models;

import com.knoettner.hhuddle.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn( name = "house_id_FK")
    private House house;

    @OneToMany(mappedBy = "board")
    Set<UserPost> userPosts;
}

package com.knoettner.hhuddle.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
public class Facility {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "facility_type",nullable = false)
    private String type;
    private String description;

    @ManyToOne
    @JoinColumn(name = "house_id_FK")
    private House house;


    @OneToMany (mappedBy = "facility")
    private Set<Post> posts;

}

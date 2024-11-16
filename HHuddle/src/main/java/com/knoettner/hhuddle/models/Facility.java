package com.knoettner.hhuddle.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class Facility {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "facility_type",nullable = false)
    private String type;
    private String description;

    @ManyToMany (mappedBy = "facilities", fetch = FetchType.EAGER)
    private Set<House> house;

    @OneToMany (mappedBy = "facility")
    private Set<Post> posts;
}

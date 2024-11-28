package com.knoettner.hhuddle.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String adress;
    @OneToMany(mappedBy =  "house", fetch = FetchType.EAGER)
    private Set<Board> boards;

    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER)
    private Set<MyUser> residents;

    @OneToMany (mappedBy = "house",fetch = FetchType.EAGER)
    private Set<Facility> facilities;
}

package com.knoettner.hhuddle.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class House {
    @Id
    private Long id;
    private String adress;
    @OneToMany(mappedBy =  "house")
    private Set<Board> boards;

    @OneToMany(mappedBy = "house")
    private Set<MyUser> residents;

    @OneToMany (mappedBy = "house",fetch = FetchType.EAGER)
    private Set<Facility> facilities;
}

package com.knoettner.hhuddle.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class House {
    @Id
    private int id;
    private String adress;
    @OneToMany(mappedBy =  "house")
    private Set<Board> boards;

    @OneToMany(mappedBy = "house")
    private Set<MyUser> residents;

    @ManyToMany
    @JoinTable(
            name = "house_facilities",
            joinColumns = @JoinColumn(name = "house_id_FK"),
            inverseJoinColumns = @JoinColumn(name = "facility_id_FK")
    )
    private Set<Facility> facilities;
}

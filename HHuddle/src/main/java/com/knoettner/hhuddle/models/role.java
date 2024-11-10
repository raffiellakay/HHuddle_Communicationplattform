package com.knoettner.hhuddle.models;

import com.knoettner.hhuddle.UserRole;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class role {
    @Id
    private int id;
    private UserRole type;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)

    private Set<user> users;


}

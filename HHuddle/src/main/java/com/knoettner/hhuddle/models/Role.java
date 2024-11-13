package com.knoettner.hhuddle.models;

import com.knoettner.hhuddle.UserRole;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Role {
    @Id
    private int id;
    @Column( nullable = false)
    private UserRole type;
    @ManyToMany(mappedBy = "roles")
    private Set<MyUser> Users;


}

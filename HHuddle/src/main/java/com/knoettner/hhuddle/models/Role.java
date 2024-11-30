package com.knoettner.hhuddle.models;

import com.knoettner.hhuddle.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    private Long id;
    @Column( nullable = false)
    private UserRole type;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<MyUser> Users;





}

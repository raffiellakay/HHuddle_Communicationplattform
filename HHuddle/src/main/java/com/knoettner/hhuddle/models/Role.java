package com.knoettner.hhuddle.models;

import com.knoettner.hhuddle.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Role {
    @Id
    private Long id;
    @Column( nullable = false)
    private UserRole type;
    @ManyToMany(mappedBy = "roles")
    private Set<MyUser> Users;





}

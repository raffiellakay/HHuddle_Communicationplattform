package com.knoettner.hhuddle.dto;

import com.knoettner.hhuddle.models.Board;
import com.knoettner.hhuddle.models.Facility;
import com.knoettner.hhuddle.models.MyUser;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HouseDto {
    private int id;
    private String adress;
    private Set<MyUser> residents;
    private Set<Facility> facilities;
}

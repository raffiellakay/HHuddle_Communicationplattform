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
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class HouseDto {
    private Long id;
    private String address;
    private Set<MyUserDto> residents;
    private Set<FacilityDto> facilities;
}

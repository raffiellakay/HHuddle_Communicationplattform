package com.knoettner.hhuddle.dto;

import com.knoettner.hhuddle.UserRole;
import com.knoettner.hhuddle.models.MyUser;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;
    //ENUM in Entity
    private String type;

}

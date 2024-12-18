package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.UserRole;
import com.knoettner.hhuddle.dto.RoleDto;
import com.knoettner.hhuddle.models.Role;
import com.knoettner.hhuddle.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class RoleMapper {

    public RoleDto toDto (Role role) {
        RoleDto roleDto = new RoleDto( role.getId(),role.getType().toString());
       return roleDto;
    }

    public Role toEntity (RoleDto roleDto) {

         Role newRole = new Role(roleDto.getId(), UserRole.valueOf(roleDto.getType().toUpperCase()), new HashSet<>() );
    return newRole;
    }
}

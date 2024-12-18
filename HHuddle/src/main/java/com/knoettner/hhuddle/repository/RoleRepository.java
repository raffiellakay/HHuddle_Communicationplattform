package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.UserRole;
import com.knoettner.hhuddle.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
Optional<Role> findByType (UserRole type);

}

package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername (String username);

    Boolean existsByUsername(String username);

    Boolean existsByMail (String mail);
}

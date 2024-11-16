package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, Long> {
}

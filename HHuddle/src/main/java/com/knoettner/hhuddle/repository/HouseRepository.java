package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}

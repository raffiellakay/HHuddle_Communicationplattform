package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
}

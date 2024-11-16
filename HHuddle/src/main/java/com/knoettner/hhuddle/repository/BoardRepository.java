package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}

package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

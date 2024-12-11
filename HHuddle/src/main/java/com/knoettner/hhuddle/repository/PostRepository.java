package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTimestampAfter(LocalDateTime timestamp);//return the latest posts after a definate date
}

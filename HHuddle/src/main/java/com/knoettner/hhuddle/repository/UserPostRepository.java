package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.models.Post;
import com.knoettner.hhuddle.models.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPostRepository extends JpaRepository<UserPost, UserPostKey> {
    List<Post> findById(Long userId);
}

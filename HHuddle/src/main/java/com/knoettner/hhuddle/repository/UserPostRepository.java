package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.models.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostRepository extends JpaRepository<UserPost, UserPostKey> {
}

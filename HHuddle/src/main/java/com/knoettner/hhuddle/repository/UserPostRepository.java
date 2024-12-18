package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.models.Post;
import com.knoettner.hhuddle.models.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface UserPostRepository extends JpaRepository<UserPost, UserPostKey> {
    List<UserPost> findByUserId(Long userId);

    List<UserPost> findByUser_Id(Long userId);

}

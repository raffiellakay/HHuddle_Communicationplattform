package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.PostDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserPostService {

    @Transactional
    List<PostDto> getAllPosts();

    @Transactional









    PostDto createPost(PostDto postDto);

    PostDto createUserPost(PostDto postDto);





    PostDto updatePost(Long postId, PostDto updatedPost);

    PostDto getPost(Long postId);

    void deletePost(Long postId);
}

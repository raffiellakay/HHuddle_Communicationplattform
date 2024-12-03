package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.RequestPostDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService {

    @Transactional
    List<RequestPostDto> getAllPosts();

    @Transactional
    RequestPostDto createPost(RequestPostDto requestPostDto);





    RequestPostDto updatePost(Long postId, RequestPostDto updatedPost);

    RequestPostDto getPost(Long postId);

    void deletePost(Long postId);
}

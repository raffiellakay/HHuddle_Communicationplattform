package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.PostDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService {

    @Transactional
    List<PostDto> getAllPosts();

    @Transactional


    PostDto createBlackboardPost(PostDto post);



    PostDto createPackagePost(PostDto post);

    PostDto createExchangePost(PostDto post);

    PostDto createEventPost(PostDto post);

    PostDto updatePost(Long postId, PostDto updatedPost);

    PostDto getPost(Long postId);

    void deletePost(Long postId);
}

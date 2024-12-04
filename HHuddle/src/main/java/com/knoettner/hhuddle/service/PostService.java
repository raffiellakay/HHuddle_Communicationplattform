package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.Category;
import com.knoettner.hhuddle.dto.PostDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService {

    @Transactional
    List<PostDto> getAllPosts();

    @Transactional




    PostDto createPost(PostDto postDto, Category category);


    PostDto createBlackboardPost(PostDto postDto);

    PostDto createPackagePost(PostDto postDto);

    PostDto createEventsPost(PostDto postDto);

    PostDto createExchangePost(PostDto postDto);



    PostDto updatePost(Long postId, PostDto updatedPost);

    PostDto getPost(Long postId);

    void deletePost(Long postId);
}

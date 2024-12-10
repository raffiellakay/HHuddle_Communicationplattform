package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.models.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserPostService {

    @Transactional
    List<PostDto> getAllPosts();

    @Transactional



    PostDto createUserPost(PostDto postDto);





    PostDto updateUserPost( PostDto updatedPost);

    PostDto getPost(Long postId);

    PostDto getAllPost(Post post);

    void deletePost(Long postId);
}

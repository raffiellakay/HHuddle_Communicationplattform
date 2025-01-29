package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.FacilityDto;
import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.models.Facility;
import com.knoettner.hhuddle.models.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface UserPostService {

    @Transactional
    Set<PostDto> getAllPosts();

    @Transactional


    PostDto createUserPost(PostDto postDto);


  //  PostDto createBlackboardPost(PostDto postDto);

    PostDto updateUserPost(PostDto updatedPost);

    PostDto getPost(Long postId);



   ;


    Set<PostDto> getPostsByUserId(Long userId);

    void deletePost(Long postId);

    void deletePostsByUserId(Long userId);

    Set<PostDto> getPostsByHouseId(Long houseId);

    Long getBoardIdByHouseIdAndCategory(Long houseId, String category) ;



}

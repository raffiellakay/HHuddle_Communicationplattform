package com.knoettner.hhuddle.dto.mapper;


import com.knoettner.hhuddle.Category;
import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.BasicUserDto;
import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.models.Facility;
import com.knoettner.hhuddle.models.Post;
import com.knoettner.hhuddle.models.UserPost;
import com.knoettner.hhuddle.repository.FacilityRepository;
import com.knoettner.hhuddle.repository.UserPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostMapper {
    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    UserPostRepository userPostRepository;

    public PostDto toDto (Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setCategory(post.getCategory().toString());
        postDto.setTimestamp(post.getTimestamp());
        postDto.setTitle(post.getTitle());
        postDto.setText(post.getText());

        postDto.setAnonymous(post.isAnonymous());
        postDto.setPrivate(post.isPrivate());

        if (post.getStarttime() != null) {
            postDto.setStarttime(post.getStarttime());
        }
        if (post.getEndtime() != null) {
            postDto.setEndtime(post.getEndtime());
        }
        if (post.getPhoto() != null) {
            postDto.setPhoto(post.getPhoto());
        }
        if (post.getFacility() != null) {
            postDto.setFacilityId(post.getFacility().getId());
        }

        if (post.getUserPost() != null) {
            postDto.setBoardId(post.getUserPost().getBoard().getId());
            // TODO: map user to basicuserdto
            BasicUserDto basicUserDto = new BasicUserDto();
            postDto.setUser(basicUserDto);
        }

        return postDto;
    }

    public Post toEntity (PostDto postdto) {
        Post post = new Post();
        post.setId(postdto.getId());
        post.setCategory(Category.valueOf(postdto.getCategory().toUpperCase()));
        post.setTimestamp(postdto.getTimestamp());
        post.setTitle(postdto.getTitle());
        post.setText(postdto.getText());
        post.setAnonymous(postdto.isAnonymous());
        post.setPrivate(postdto.isPrivate());

        if (postdto.getStarttime() != null) {
            post.setStarttime(postdto.getStarttime());
        }
        if (postdto.getEndtime() != null) {
            post.setEndtime(postdto.getEndtime());
        }
        if (postdto.getPhoto() != null) {
            post.setPhoto(postdto.getPhoto());
        }
        if (postdto.getFacilityId() != null) {
            Optional<Facility> maybeFacility = facilityRepository.findById(postdto.getFacilityId());
            if (maybeFacility.isPresent()) {
                post.setFacility(maybeFacility.get());
            }

        }
        if (postdto.getId() != null && postdto.getUser() != null && postdto.getBoardId() != null) {
            // TODO: change 1L to correct userId
            UserPostKey userPostKey = new UserPostKey(1L, postdto.getId(), postdto.getBoardId());
            Optional<UserPost> maybeUserPosts = userPostRepository.findById(userPostKey);
            if (maybeUserPosts.isPresent()) {
                post.setUserPost(maybeUserPosts.get());
            }

        }
        return post;


    }
}

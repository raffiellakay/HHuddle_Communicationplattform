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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
        if (post.getPathToPhoto() != null) {
            try {
                postDto.setPhoto(Files.readAllBytes(Paths.get(post.getPathToPhoto())));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "photo not found");
            }
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
//if post is not saved in the database, delete the image
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
            String fileName= "./images/photo_"+ LocalDateTime.now() +".jpg";
            try(OutputStream out =  new FileOutputStream(fileName)) {

                out.write(postdto.getPhoto());
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            post.setPathToPhoto(fileName);

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

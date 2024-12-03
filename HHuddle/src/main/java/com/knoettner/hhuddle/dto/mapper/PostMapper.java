package com.knoettner.hhuddle.dto.mapper;


import com.knoettner.hhuddle.Category;
import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.BasicUserDto;
import com.knoettner.hhuddle.dto.RequestPostDto;
import com.knoettner.hhuddle.models.Facility;
import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.models.Post;
import com.knoettner.hhuddle.models.UserPost;
import com.knoettner.hhuddle.repository.FacilityRepository;
import com.knoettner.hhuddle.repository.UserPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Optional;

@Component
public class PostMapper {
    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    UserPostRepository userPostRepository;

    @Autowired
    BasicUserMapper basicUserMapper;

    @Autowired
    BoardMapper boardMapper;

    public RequestPostDto toDto (Post post) {
        RequestPostDto requestPostDto = new RequestPostDto();
        requestPostDto.setId(post.getId());
        requestPostDto.setBord(boardMapper.toDto(post.getBoard()));// check once again!
        requestPostDto.setTitle(post.getTitle());
        requestPostDto.setText(post.getText());

        requestPostDto.setAnonymous(post.isAnonymous());
        requestPostDto.setPrivate(post.isPrivate());

        if (post.getStarttime() != null) {
            requestPostDto.setStarttime(post.getStarttime());
        }
        if (post.getEndtime() != null) {
            requestPostDto.setEndtime(post.getEndtime());
        }
        if (post.getPathToPhoto() != null) {
            try {
                requestPostDto.setPhoto(Files.readAllBytes(Paths.get(post.getPathToPhoto())));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "photo not found");
            }
        }
        if (post.getFacility() != null) {
            requestPostDto.setFacilityId(post.getFacility().getId());
        }

        if (post.getUserPost() != null) {
            requestPostDto.setBoardId(post.getUserPost().getBoard().getId());
            MyUser user = post.getUserPost().getUser();
            BasicUserDto basicUserDto = basicUserMapper.toDto(user);
            requestPostDto.setUser(basicUserDto);
        }

        return requestPostDto;
    }
//if post is not saved in the database, delete the image
    public Post toEntity (RequestPostDto postdto) {
        Post post = new Post();
        post.setId(postdto.getId());
        post.setBoard(boardMapper.toEntity(postdto.getBord()));
        post.setTitle(postdto.getTitle());
        post.setText(postdto.getText());
        post.setAnonymous(postdto.isAnonymous());
        post.setPrivate(postdto.isPrivate());

        if (postdto.getStarttime() != null) {
            post.setStarttime(LocalDateTime.now());
        }
        if (postdto.getEndtime() != null) {
            post.setEndtime(LocalDateTime.now().plusDays(14));
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
            Long userId = postdto.getUser().getId();
            UserPostKey userPostKey = new UserPostKey(postdto.getBoardId(),userId, postdto.getId());
            Optional<UserPost> maybeUserPosts = userPostRepository.findById(userPostKey);
            if (maybeUserPosts.isPresent()) {
                post.setUserPost(maybeUserPosts.get());
            }

        }
        return post;


    }
}

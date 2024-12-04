package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.Category;
import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.dto.mapper.PostMapper;
import com.knoettner.hhuddle.models.Post;
import com.knoettner.hhuddle.repository.BoardRepository;
import com.knoettner.hhuddle.repository.PostRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UserPostServiceImpl implements UserPostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;


    @Override
    public List<PostDto> getAllPosts() {
        return List.of();
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        return null;
    }

    @Override
    public PostDto createUserPost(PostDto postDto) {
        // Feldkondition
        if (postDto.getCategory() == null || postDto.getTitle() == null || postDto.getText() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }


        // Kategorie behandeln
        Category category;
        try {
            category = Category.valueOf(postDto.getCategory().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid category");
        }
        if ((category == Category.EVENTS || category == Category.PACKAGE) && postDto.getPhoto() != null) {
            postDto.setPhoto(null); // kein foto für events and package post
        }

        // Exclude ADMIN category
        if (category == Category.FRONTPAGE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ADMIN category is not allowed for posts");
        }

        // Map DTO to entity
        Post post = postMapper.toEntity(postDto);
        post.setCategory(category);

        // Save the post in the repository
        Post savedPost = postRepository.save(post);

        // If photo was saved but post was not successfully saved, delete the image
        if (post.getPathToPhoto() != null && savedPost == null) {
            try {
                Files.deleteIfExists(Paths.get(post.getPathToPhoto()));
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image after save failure", e);
            }
        }

        // Map saved entity back to DTO
        return postMapper.toDto(savedPost);
    }

    @Override
    public PostDto updatePost(Long postId, PostDto updatedPost) {
        return null;
    }

    @Override
    public PostDto getPost(Long postId) {
        return null;
    }

    @Override
    public void deletePost(Long postId) {

    }




   /* @Override
    public PostDto createBlackBoardPost(PostDto post) {
        Post newBlackboardPost = new Post();
        newBlackboardPost.setText(post.getText());
        newBlackboardPost.setTitle(post.getTitle());
        newBlackboardPost.setStarttime(LocalDateTime.now());
        newBlackboardPost.setEndtime(LocalDateTime.now().plusDays(14));
        newBlackboardPost.setCategory(Category.BLACKBOARD);
        newBlackboardPost.setPathToPhoto(post.getPhoto());

    }*/







}

   /* @Override
    public PostDto updatePost(Long postId, PostDto updatedPost) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setText(updatedPost.getText());
        existingPost.setTimestamp(updatedPost.getTimestamp());

      /*  switch (existingPost.getCategory()) {
            case EVENTS:
                //existingPost.setPhoto(updatedPost.getPhoto());
                existingPost.setStarttime(updatedPost.getStarttime());
                existingPost.setEndtime(updatedPost.getEndtime());

                break;
            case EXCHANGE:
             //   existingPost.setPhoto(updatedPost.getPhoto());

                break;

            case

        }*/











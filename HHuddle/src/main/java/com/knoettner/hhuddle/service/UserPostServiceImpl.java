package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.Category;
import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.dto.mapper.PostMapper;
import com.knoettner.hhuddle.models.Board;
import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.models.Post;
import com.knoettner.hhuddle.models.UserPost;
import com.knoettner.hhuddle.repository.BoardRepository;
import com.knoettner.hhuddle.repository.PostRepository;
import com.knoettner.hhuddle.repository.UserPostRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private UserPostRepository userPostRepository;


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
        // Feldvalidierung
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

        // Kategorie-Einschränkungen
        if ((category == Category.EVENTS || category == Category.PACKAGE) && postDto.getPhoto() != null) {
            postDto.setPhoto(null); // Kein Foto für EVENTS und PACKAGE erlaubt
        }

        if (category == Category.FRONTPAGE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "FRONTPAGE category is not allowed for posts");
        }

        // Neues Post-Objekt erstellen
        Post newPost = new Post();
        newPost.setText(postDto.getText());
        newPost.setCategory(category);
        newPost.setTitle(postDto.getTitle());
        newPost.setTimestamp(LocalDateTime.now());

        // Post speichern
        Post savedPost = postRepository.save(newPost);

        // UserPostKey erstellen
        Long userId = postDto.getUser().getId();
        Long boardId = postDto.getBoardId();
        Long postId = savedPost.getId();
        UserPostKey userPostKey = new UserPostKey(boardId, userId, postId);

        Optional<Board> maybeBoard = boardRepository.findById(boardId);
        Optional<MyUser> maybeUser = userRepository.findById(userId);

        if (maybeBoard.isPresent() && maybeUser.isPresent()) {
            UserPost userPost = new UserPost(userPostKey, maybeBoard.get(), maybeUser.get(), savedPost);
            userPostRepository.save(userPost);
        }

        // Wenn das Bild nicht benötigt wurde und es existiert, löschen
        if (newPost.getPathToPhoto() != null && savedPost == null) {
            try {
                Files.deleteIfExists(Paths.get(newPost.getPathToPhoto()));
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image after save failure", e);
            }
        }

        // DTO zurückgeben
        postDto.setId(savedPost.getId());
        return postDto;
    }


   /* public PostDto createUserPost(PostDto postDto) {
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

        // Exclude Frontpage category
        if (category == Category.FRONTPAGE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "FRONTPAGE category is not allowed for posts");
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
*/
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











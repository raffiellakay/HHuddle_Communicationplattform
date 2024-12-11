package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.Category;
import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.dto.mapper.PostMapper;
import com.knoettner.hhuddle.models.*;
import com.knoettner.hhuddle.repository.BoardRepository;
import com.knoettner.hhuddle.repository.PostRepository;
import com.knoettner.hhuddle.repository.UserPostRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

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



        if (category != Category.EVENTS) { //exclude the fields from all categories except EVENTS
            postDto.setStarttime(null);
            postDto.setEndtime(null);
            postDto.setPrivate(false);
            postDto.setFacilityId(null);
        } else {
            if(postDto.getStarttime()==null || postDto.getEndtime() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Starttime and Endtime are required for EVENTS");
            }

        }

        // Neues Post-Objekt erstellen
        Post newPost = postMapper.toEntity(postDto);
        newPost.setTimestamp(LocalDateTime.now());
        newPost.setId(null);//Autoincrement ID

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


        // DTO zurückgeben
        postDto.setId(savedPost.getId());
        return postDto;
    }
    @Override
    public PostDto updateUserPost( PostDto updatedPost) {
        Post post = postMapper.toEntity(updatedPost);
        Long id = post.getId();
        Optional <Post> dbpost = postRepository.findById(id);
        if (!dbpost.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post with this id not exist");
        postRepository.deleteById(id);
        postRepository.save(post);
        return updatedPost;
    }

    @Override
    public PostDto getPost(Long postId) {
        Optional <Post> dbpost = postRepository.findById(postId);
        if (!dbpost.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post with this id not exist");

        return postMapper.toDto(dbpost.get());
    }




   /* @Override
    public Set<HouseDto> getAllHouses() {
        List<House> allHouses = houseRepository.findAll();
        Set<HouseDto> houseSet = new HashSet<>();
        for (House currentHouse : allHouses) {
            HouseDto houseDto = houseMapper.todDto(currentHouse);
            houseSet.add(houseDto);
        }

        return houseSet;*/

    @Override
    public Set<PostDto> getAllPosts() {
        List<Post> listOfPosts = postRepository.findByTimestampAfter(LocalDateTime.now().minusDays(14)); // show posts of previous 2 weeks
        Set<PostDto> postSet = new HashSet<>();// for unique Objects
        for (Post currentPost : listOfPosts){
            PostDto postDto = postMapper.toDto(currentPost);
            postSet.add(postDto);
        }
        return postSet;
    }

    @Override
    public Set<PostDto> getPostsByUserId(Long userId){
        List<Post>userPosts = userPostRepository.findById(userId);
        Set<PostDto> postSet = new HashSet<>();
        for (Post post: userPosts) {
            PostDto postDto = postMapper.toDto(post);
            postSet.add(postDto);
        }


        return postSet;
    }


    /*@Override
    public void deletePost( Long postId) {
        Optional <Post> dbpost = postRepository.findById(postId);
        if (!dbpost.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post with this id not exist");
        postRepository.deleteById(postId);
    }*/


    @Override
    public void deletePost(Long postId) {
        Optional<Post> maybePost = postRepository.findById(postId);
        if (maybePost.isPresent()) {
            userPostRepository.deleteById(maybePost.get().getUserPost().getId());
            postRepository.deleteById(postId);
        }
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











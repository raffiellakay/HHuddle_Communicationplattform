package com.knoettner.hhuddle.controller;

import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.service.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/posts")
public class UserPostController {
    @Autowired
    private UserPostService userPostService;

    @PostMapping("/post")
    public ResponseEntity<PostDto> createUserPost(@RequestBody PostDto postDto) {
        PostDto createdPost = userPostService.createUserPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/update")
    public ResponseEntity<PostDto> updateUserPost(@RequestBody PostDto postDto) {
        PostDto updatedPost = userPostService.updateUserPost(postDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto>  getPost(@PathVariable("id") Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(userPostService.getPost(postId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/allposts")
    public ResponseEntity  <Set<PostDto>> getAllPosts(){
        Set<PostDto> allPosts = userPostService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(allPosts);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user{id}")
    public ResponseEntity<Set<PostDto>> getPostsByUserId(@PathVariable("id") Long userId){
        Set<PostDto>userPosts = userPostService.getPostsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userPosts);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/post/{id}")
    void deletePost(@PathVariable("id") Long postId) {
        userPostService.deletePost(postId);
    }

     @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deletePostsByUserId(@PathVariable("userId") Long userId) {
        userPostService.deletePostsByUserId(userId);
        return ResponseEntity.noContent().build();
    }

}


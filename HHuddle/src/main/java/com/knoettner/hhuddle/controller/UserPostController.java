package com.knoettner.hhuddle.controller;

import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.service.UserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class UserPostController {
    @Autowired
    private UserPostService userPostService;

    @PostMapping("/userpost")
    public ResponseEntity<PostDto> createUserPost(@RequestBody PostDto postDto) {
        PostDto createdPost = userPostService.createUserPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    /*@PostMapping("/package")
    public ResponseEntity<PostDto> createPackagePost(@RequestBody PostDto postDto) {
        PostDto createdPost = postService.createPackagePost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PostMapping("/events")
    public ResponseEntity<PostDto> createEventsPost(@RequestBody PostDto postDto) {
        PostDto createdPost = postService.createEventsPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PostMapping("/exchange")
    public ResponseEntity<PostDto> createExchangePost(@RequestBody PostDto postDto) {
        PostDto createdPost = postService.createExchangePost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }*/
}
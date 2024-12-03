package com.knoettner.hhuddle.controller;

import com.knoettner.hhuddle.dto.RequestPostDto;
import com.knoettner.hhuddle.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<RequestPostDto> createPost(@RequestBody RequestPostDto requestPostDto) {
        RequestPostDto createdPost = postService.createPost(requestPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

}
package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.dto.mapper.PostMapper;
import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.repository.PostRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserRepository userRepository;



    @Transactional
    @Override
    public PostDto createPost(Long userId, Long boardId){
        MyUser user = userRepository.findById(userId)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return null;
    }



    }


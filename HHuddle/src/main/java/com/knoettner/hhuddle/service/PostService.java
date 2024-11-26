package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.PostDto;
import org.springframework.transaction.annotation.Transactional;

public interface PostService {

    @Transactional
    PostDto createPost(Long user, Long boardId);
}

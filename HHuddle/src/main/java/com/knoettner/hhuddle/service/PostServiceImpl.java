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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl  implements PostService {
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
        public PostDto createPost(PostDto postDto, Category category) {
            Post post = postMapper.toEntity(postDto);
            post.setCategory(category);
            post.setTimestamp(LocalDateTime.now());
            postRepository.save(post);
            return postMapper.toDto(post);
        }

        @Override
        public PostDto createBlackboardPost(PostDto postDto) {
            return createPost(postDto, Category.BLACKBOARD);
        }

        @Override
        public PostDto createPackagePost(PostDto postDto) {
            return createPost(postDto, Category.PACKAGE);
        }

        @Override
        public PostDto createEventsPost(PostDto postDto) {
            return createPost(postDto, Category.EVENTS);
        }

        @Override
        public PostDto createExchangePost(PostDto postDto) {
            return createPost(postDto, Category.EXCHANGE);
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











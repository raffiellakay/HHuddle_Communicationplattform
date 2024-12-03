package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.dto.RequestPostDto;
import com.knoettner.hhuddle.dto.mapper.PostMapper;
import com.knoettner.hhuddle.models.Post;
import com.knoettner.hhuddle.repository.BoardRepository;
import com.knoettner.hhuddle.repository.PostRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<RequestPostDto> getAllPosts() {
        return List.of();
    }

    @Override
    public RequestPostDto createPost(RequestPostDto requestPostDto) {
        Post post = postMapper.toEntity(requestPostDto);
        post.setTimestamp(LocalDateTime.now());
        postRepository.save(post);
        return postMapper.toDto(post);
    }



    @Override
    public RequestPostDto updatePost(Long postId, RequestPostDto updatedPost) {
        return null;
    }

    @Override
    public RequestPostDto getPost(Long postId) {
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











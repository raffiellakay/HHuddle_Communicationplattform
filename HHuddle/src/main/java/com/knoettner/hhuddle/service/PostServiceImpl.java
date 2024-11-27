package com.knoettner.hhuddle.service;

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

import static com.knoettner.hhuddle.Category.*;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;




    @Override
    public PostDto createBlackboardPost(PostDto post) {
        Post blackboardPost = new Post();
        blackboardPost.setTitle(post.getTitle());
        blackboardPost.setText(post.getText());
        blackboardPost.setCategory(BLACKBOARD);
        blackboardPost.setTimestamp(LocalDateTime.now());
        blackboardPost.setAnonymous(post.isAnonymous());
        blackboardPost.setPhoto(post.getPhoto());
        postRepository.save(blackboardPost);
        blackboardPost.setId(blackboardPost.getId());

        return post;
    }


    @Override
    public PostDto createPackagePost(PostDto post) {
        Post packagePost = new Post();
        packagePost.setTitle(post.getTitle());
        packagePost.setText(post.getText());
        packagePost.setCategory(PACKAGE);
        packagePost.setTimestamp(LocalDateTime.now());
        packagePost.setPhoto(post.getPhoto());
        postRepository.save(packagePost);
        packagePost.setId(packagePost.getId());

        return post;

    }


    @Override
    public PostDto createExchangePost(PostDto post) {
        Post exchangePost = new Post();
        exchangePost.setTitle(post.getTitle());
        exchangePost.setText(post.getText());
        exchangePost.setCategory(EXCHANGE);
        exchangePost.setTimestamp(LocalDateTime.now());
        exchangePost.setPhoto(post.getPhoto());
        postRepository.save(exchangePost);
        exchangePost.setId(exchangePost.getId());

        return post;

    }


    @Override
    public PostDto createEventPost(PostDto post) {
        Post EventPost = new Post();
        EventPost.setTitle(post.getTitle());
        EventPost.setText(post.getText());
        EventPost.setCategory(EXCHANGE);
        EventPost.setTimestamp(LocalDateTime.now());
        EventPost.setPhoto(post.getPhoto());
        EventPost.setStarttime(post.getStarttime());
        EventPost.setEndtime(post.getEndtime());
        postRepository.save(EventPost);
        EventPost.setId(EventPost.getId());

        return post;

    }
    

    @Override
    public PostDto getPost(Long postId){
        Optional<Post> maybePost = postRepository.findById(postId);
        if (maybePost.isPresent()){
            Post post = maybePost.get();
            Post savedPost = postRepository.save(post);
            return postMapper.toDto(savedPost);
        }
        throw new RuntimeException();


    }


    @Override
    public List<PostDto> getAllPosts() {
        //log.info??
        List<Post> allPosts = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : allPosts) {
            PostDto postDto = postMapper.toDto(post);
            postDtos.add(postDto);
        }
        return postDtos;
    }

    @Override
    public void deletePost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Post not found"));
        postRepository.delete(post);

    }


    }







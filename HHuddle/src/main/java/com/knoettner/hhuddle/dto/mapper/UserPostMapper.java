package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.UserPostDto;
import com.knoettner.hhuddle.models.Board;
import com.knoettner.hhuddle.models.MyUser;
import com.knoettner.hhuddle.models.Post;
import com.knoettner.hhuddle.models.UserPost;
import com.knoettner.hhuddle.repository.BoardRepository;
import com.knoettner.hhuddle.repository.PostRepository;
import com.knoettner.hhuddle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPostMapper {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    public UserPostDto toDto (UserPost userpost) {
        UserPostDto userPostDto = new UserPostDto(userpost.getPost().getId(), userpost.getUser().getId(), userpost.getBoard().getId());
        return userPostDto;
    }

    public UserPost toEntity (UserPostDto userPostDto) {
        Optional<Post> maybePost = postRepository.findById(userPostDto.getPostId());
        Optional<MyUser> maybeUser = userRepository.findById(userPostDto.getUserId());
        Optional<Board> maybeBoard = boardRepository.findById(userPostDto.getBoardId());
        if (maybeBoard.isPresent() && maybeUser.isPresent() && maybePost.isPresent()) {
            UserPostKey userPostKey = new UserPostKey(maybeBoard.get().getId(), maybeUser.get().getId(), maybePost.get().getId());
            UserPost userPost = new UserPost(userPostKey, maybeBoard.get(), maybeUser.get(), maybePost.get());
            return userPost;
        } else {
            return null;
        }
    }
}

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
        newPost.setId(null);//Autoincrement ID. Damit das Risiko dass schon bestehndes Post upgedatet wird, falls Id manuell eingegeben wird und bereits existiert.

        // Post speichern
        Post savedPost = postRepository.save(newPost);

        // UserPostKey erstellen
        Long userId = postDto.getUser().getId();   // ????
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
    //funktioniert nicht? löscht nur alten Post erstellt keinen neuen
    @Override
    public PostDto updateUserPost( PostDto updatedPost) {//TODO einzelne Felder updaten
        Post post = postMapper.toEntity(updatedPost);
        Long id = post.getId();
        Optional <Post> dbpost = postRepository.findById(id);// sucht nach einem post in db
        if (!dbpost.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post with this id not exist");
        postRepository.deleteById(id); //löscht bestehdes Post in DB
        postRepository.save(post);// speichert das upgedated Post neu
        return updatedPost;
    }

    @Override
    public PostDto getPost(Long postId) {
        Optional <Post> dbpost = postRepository.findById(postId);
        if (!dbpost.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post with this id not exist");

        return postMapper.toDto(dbpost.get());
    }


    @Override
    public Set<PostDto> getAllPosts() {//TODO ? Nach Kategorien differenzieren
        List<Post> listOfPosts = postRepository.findByTimestampAfter(LocalDateTime.now().minusDays(14)); // show posts of previous 2 weeks
        Set<PostDto> postSet = new HashSet<>();// for unique Objects
        for (Post currentPost : listOfPosts){
            PostDto postDto = postMapper.toDto(currentPost);
            postSet.add(postDto);// fügt das Element zur Sammlung hinzu
        }
        return postSet;
    }

    @Override
    public Set<PostDto> getPostsByUserId(Long userId){

            List<UserPost> userPosts = userPostRepository.findByUserId(userId);
            Set<PostDto> postSet = new HashSet<>();
            for (UserPost userPost : userPosts) {
                PostDto postDto = postMapper.toDto(userPost.getPost());
                postSet.add(postDto);
            }

            return postSet;
        }



        @Override
    public void deletePost(Long postId) {
        Optional<Post> maybePost = postRepository.findById(postId);
        if (maybePost.isPresent()) {
            userPostRepository.deleteById(maybePost.get().getUserPost().getId());
            postRepository.deleteById(postId);
        }
    }


     @Override

    public void deletePostsByUserId(Long userId) {
         List<UserPost> userPosts = userPostRepository.findByUserId(userId);
         for (UserPost userPost : userPosts) {
             Post post = userPost.getPost();
             if (post != null) {
                 post.setUserPost(null); // Bricht die Beziehung zwischen Post und UserPost
                 postRepository.save(post); // speichert Post mit UserPost(null)
                 postRepository.delete(post); // löscht das post
             }

             // löscht userpost
             userPostRepository.delete(userPost);
         }


     }
}









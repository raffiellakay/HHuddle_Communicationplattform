package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.Category;
import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.dto.mapper.HouseMapper;
import com.knoettner.hhuddle.dto.mapper.PostMapper;
import com.knoettner.hhuddle.models.*;
import com.knoettner.hhuddle.repository.*;
import com.knoettner.hhuddle.security.services.UserDetailsImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

import static com.knoettner.hhuddle.Category.BLACKBOARD;

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
    @Autowired
    private HouseRepository houseRepository;
  


    @Override
    public PostDto createUserPost(PostDto postDto) { //TODO  teilen Verschiedenen Funktionen, leichter mit FE verbinden

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
        if ((category == Category.EVENTS || category == Category.PACKAGE) && postDto.getPathToImage() != null) {
            postDto.setPathToImage(null); // Kein Foto für EVENTS und PACKAGE erlaubt
        }



        if (category != Category.EVENTS) { //exclude the fields from all categories except EVENTS
            postDto.setStarttime(null);
            postDto.setEndtime(null);
            postDto.setPrivate(false);
            postDto.setFacilityId(null);
        } else {
            if(postDto.getStarttime()==null || postDto.getEndtime() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Starttime  and Endtime are required for EVENTS");
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

   /*
    @Override
    public PostDto createBlackboardPost(PostDto postDto) {
        Post newBlackboardPost = postMapper.toEntity(postDto);
        newBlackboardPost.setTimestamp(LocalDateTime.now());
        newBlackboardPost.setCategory(BLACKBOARD);

        Post savedBlackboardPost= postRepository.save(newBlackboardPost);
        Long userId = postDto.getUser().getId();
        Long boardId = postDto.getBoardId();
        Long postId = savedBlackboardPost.getId();
        UserPostKey userPostKey = new UserPostKey(boardId, userId, postId);
        Optional<Board> maybeBoard = boardRepository.findById(boardId);
        Optional<MyUser> maybeUser = userRepository.findById(userId);
        if (maybeBoard.isPresent() && maybeUser.isPresent()) {
            UserPost userPost = new UserPost(userPostKey, maybeBoard.get(), maybeUser.get(), savedBlackboardPost);
            userPostRepository.save(userPost);
        }

        postDto.setId(savedBlackboardPost.getId());
        return postDto;



    }
    */






    @Override
    public PostDto updateUserPost( PostDto updatedPost) {//TODO einzelne Felder updaten
        Post post = postMapper.toEntity(updatedPost);
        Long id = post.getId();
        Optional <Post> dbpost = postRepository.findById(id);// sucht nach einem post in db
        if (!dbpost.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post with this id not exist");
        postRepository.updatePost(
                post.getId(),
                post.getEndtime(),
                post.isAnonymous(),
                post.isPrivate(),
                post.getPathToImage(),
                post.getStarttime(),
                post.getText(),
                post.getTitle(),
                post.getFacility()
        );
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
    public Set<PostDto> getAllPosts() {//TODO ? Nach Kategorien differenzieren, weil???
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

    @Override
    public Set<PostDto> getPostsByHouseId(Long houseId) {
        //securitycontextholder gets authenticationinfo
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //gets special user details (=principals)
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        Optional<MyUser> maybeUser = userRepository.findById(userId);
        if (maybeUser.isEmpty()) {
            return null;
        }

        MyUser user = maybeUser.get();
        Long houseIdFromUser = user.getHouse().getId();

        if(houseIdFromUser != houseId) {
            return null;
        }

        List<Post> allPosts = postRepository.findAll();
        Set<PostDto> allPostfromHouse = new HashSet<>();
        for (Post currentPost : allPosts) {
            //if one post is not correctly in databank and has no userPost Connection it would throw error, so there is a try catch and an fixed Id to avoid that
            Long id = -1L;
            try {
                id = currentPost.getUserPost().getBoard().getHouse().getId();
            } catch (Exception e) {
                id = -1L;
            }
            if (id == houseId) {
              allPostfromHouse.add(postMapper.toDto(currentPost));
            }
        }
        return allPostfromHouse;
    }

    @Override
    public Long getBoardIdByHouseIdAndCategory(Long houseId, String category) {
        Optional<House> maybeHouse = houseRepository.findById(houseId);
        if (maybeHouse.isPresent()) {
            House house = maybeHouse.get();
            Set<Board> boards = house.getBoards();
            for (Board currentBoard : boards) {
                if (currentBoard.getCategory().toString().equalsIgnoreCase(category)) {
                   return currentBoard.getId();
                }
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "House not found");

    }
}









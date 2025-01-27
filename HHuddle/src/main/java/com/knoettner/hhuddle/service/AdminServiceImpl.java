package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.*;
import com.knoettner.hhuddle.dto.mapper.FacilityMapper;
import com.knoettner.hhuddle.dto.mapper.HouseMapper;
import com.knoettner.hhuddle.dto.mapper.MyUserMapper;
import com.knoettner.hhuddle.dto.mapper.PostMapper;
import com.knoettner.hhuddle.models.*;
import com.knoettner.hhuddle.repository.*;
import com.knoettner.hhuddle.security.modelsDtos.EmailDetails;
import com.knoettner.hhuddle.security.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.knoettner.hhuddle.Category.*;
import static com.knoettner.hhuddle.UserRole.ROLE_PMANAGEMENT;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    HouseRepository houseRepository;

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostMapper postMapper;

    @Autowired
    private FacilityMapper facilityMapper;

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    MyUserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserPostRepository userPostRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmailService emailService;

    //////////////////////// HOUSE ////////////////////

    @Override
    public HouseDto createHouse(HouseDto house) {
        House realHouse = houseMapper.toEntity(house);
        houseRepository.save(realHouse);
       //houseDTO gets same ID as real house
        house.setId(realHouse.getId());
        //calls method:
        createFacilityForHouse(house);
        //creates Boards for House
        createBoardsForHouse(realHouse.getId());
        return house;
    }

    private void createFacilityForHouse(HouseDto house) {
        Optional<House> maybeHouse = houseRepository.findById(house.getId());
        if (maybeHouse.isPresent()) {
            for (FacilityDto facility : house.getFacilities()) {
                facility.setHouseId(house.getId());
                createFacility(facility);
            }
        }
    }

//thats why we can only have one Admin with diff. houses - gets all houses that are in DB
    //necessary for e.g. Hausverwaltungsstartseite
    @Override
    public Set<HouseDto> getAllHouses() {
        List<House> allHouses = houseRepository.findAll();
        Set<HouseDto> houseSet = new HashSet<>();
        //iterates over all Houses, maps to DTO and adds to houseDtoSet
        for (House currentHouse : allHouses) {
            HouseDto houseDto = houseMapper.todDto(currentHouse);
            houseSet.add(houseDto);
        }
        return houseSet;
    }
    @Override
    public HouseDto getHouseById (Long houseId)  {
        Optional<House> maybeHouse = houseRepository.findById(houseId);
        if (maybeHouse.isPresent()) {
            return houseMapper.todDto(maybeHouse.get());
        } else {
            return null;

        }

    }
    @Override
    public void deleteHouseById(Long id) {
        Optional<House> maybeHouse = houseRepository.findById(id);
        if (maybeHouse.isPresent()) {
            House house = maybeHouse.get();
            List<Post> allPosts = postRepository.findAll();
            //deletes everything that a house contains so there is no problem
            //iteriere über alle Boards, hol dir alle Posts vom 1. Board und lösche sie, hol dir alle Posts vom 2. Board...
            for (Board currentBoard : house.getBoards()) {
                Set<UserPost> currentUserPosts = currentBoard.getUserPosts();
                for (UserPost currentPost : currentUserPosts) {
                    UserPostKey userPostId = currentPost.getId();
                    userPostRepository.deleteById(userPostId);
                    postRepository.deleteById(userPostId.getPostId());
                }
                boardRepository.deleteById(currentBoard.getId());
            }
            //hol dir alle facilities vom House und lösche jedes einzelne
            for (Facility facility : house.getFacilities()) {
               facilityRepository.deleteById(facility.getId());
            }
            //hol dir alle residents vom house und lösche sie
            for (MyUser currentResident : house.getResidents()) {
                userRepository.deleteById(currentResident.getId());
            }

            houseRepository.deleteById(id);
        }
    }

  //creating 5 Boards for each house if methode createHouse is used
   private void createBoardsForHouse(Long houseId) {
        Optional<House> maybeHouse = houseRepository.findById(houseId);
        if (maybeHouse.isPresent()) {
            House realHouse = maybeHouse.get();
            Board Blackboard = new Board(null, BLACKBOARD, realHouse, new HashSet<>());
            Board Frontpage = new Board(null, FRONTPAGE, realHouse, new HashSet<>() );
            Board Package = new Board(null, PACKAGE, realHouse, new HashSet<>() );
            Board Events = new Board(null, EVENTS, realHouse, new HashSet<>() );
            Board Exchange = new Board(null, EXCHANGE, realHouse, new HashSet<>() );
            Set<Board> boards = new HashSet<>();
            boards.add(Blackboard);
            boards.add(Frontpage);
            boards.add(Package);
            boards.add(Events);
            boards.add(Exchange);
            boardRepository.saveAll(boards);
        }

    }

    @Override
    public Long getAdminBoardIdByHouseId(Long houseId) {
        Optional<House> maybeHouse = houseRepository.findById(houseId);
        if (maybeHouse.isPresent()) {
            House realHouse = maybeHouse.get();
            for (Board currentBoard : realHouse.getBoards()) {
                if (currentBoard.getCategory().equals(FRONTPAGE)) {
                    return currentBoard.getId();
                }
            }

        }
        return null;
    }

//////////////////////// ADMIN POST //////////////////////////////

    @Override
    public PostDto createAdminPost(PostDto post) {
        Post newAdminPost = new Post();
        newAdminPost.setText(post.getText());
        newAdminPost.setCategory(FRONTPAGE);
        newAdminPost.setTitle(post.getTitle());
        newAdminPost.setTimestamp(LocalDateTime.now());
        post.setTimestamp(LocalDateTime.now());
        postRepository.save(newAdminPost);
        //set id in dto so its same as real post
        post.setId(newAdminPost.getId());
        //save post in userpost as well
        //create userPostKey as ID for userpost
        Long userId = post.getUser().getId();
        Long boardId = post.getBoardId();
        Long postId = post.getId();
        UserPostKey userPostKey = new UserPostKey(boardId, userId, postId);

        //does board and user really exist? if yes save to userPostRepo
        Optional<Board> maybeBoard = boardRepository.findById(boardId);
        Optional<MyUser> maybeUser = userRepository.findById(userId);
        if(maybeBoard.isPresent() && maybeUser.isPresent()) {
            UserPost userPost = new UserPost(userPostKey,maybeBoard.get(), maybeUser.get(), newAdminPost);
            userPostRepository.save(userPost);
        }
        return post;
    }

    @Override
    public void deleteAdminPost(Long postId) {
        Optional<Post> maybePost = postRepository.findById(postId);
        if (maybePost.isPresent()) {
            userPostRepository.deleteById(maybePost.get().getUserPost().getId());
            postRepository.deleteById(postId);
        }
    }

    @Override
    public Set<PostDto> getAdminPostsByHouseId(Long houseId) {
        Set<PostDto> allAdminPosts = new HashSet<>();
        List<Post> allPosts = postRepository.findAll();
        for (Post currentPost : allPosts) {
            if (currentPost.getUserPost().getBoard().getHouse().getId().equals(houseId) && currentPost.getCategory().equals(FRONTPAGE)) {
                PostDto dto = postMapper.toDto(currentPost);
                allAdminPosts.add(dto);
            }

        }
        return allAdminPosts;
    }

    ////////////////////////// FACILITY //////////////////////////


    @Override
    public FacilityDto createFacility(FacilityDto facility) {
        Facility realFacility = facilityMapper.toEntity(facility);
        facilityRepository.save(realFacility);
        facility.setId(realFacility.getId());
        return facility;
    }


    @Override
    public FacilityDto updateFacility(FacilityDto facility) {
        Optional<Facility> maybeFacility = facilityRepository.findById(facility.getId());
        if (maybeFacility.isPresent()) {
            Facility realFacility = maybeFacility.get();
        //update - only updates what needs to be updated
            realFacility.setType(facility.getType());
            realFacility.setDescription(facility.getDescription());
            facilityRepository.save(realFacility);
            return facilityMapper.toDto(realFacility);
        }
      return null;
    }

    @Override
    public void deleteFacilityById(Long id) {

        Optional<Facility> maybeFacility = facilityRepository.findById(id);
        if (maybeFacility.isPresent()) {
            Facility facility = maybeFacility.get();
            Set<Post> posts = facility.getPosts();
            for (Post currentPost : posts) {
                postRepository.deleteById(currentPost.getId());
            }
            facilityRepository.deleteById(id);
        }
        
    }

    @Override
    public Set<FacilityDto> getAllFacilitiesByHouseId(Long houseId) {
        Set<FacilityDto> allFacilites = new HashSet<>();
        for (Facility currentFacility : facilityRepository.findAll()) {
            if (currentFacility.getHouse().getId() == houseId) {
                allFacilites.add(facilityMapper.toDto(currentFacility));
            }
        }

        return allFacilites;
    }

    /////////////////// USER ///////////////////////////////


    @Override
    public MyUserDto createUser(MyUserDto userDto) {
        MyUser user = userMapper.toEntity(userDto);
        Optional<MyUser> maybeUser = userRepository.findByMail(user.getMail());
        if (maybeUser.isPresent()) {
            System.out.println("Diese E-Mailadresse wird bereits benutzt");
            return userDto;
        }
        //PW is set & send in sendMailToResetPW method
        user.setPassword("");
        //Random PW hashed/encoded
        //user.setPassword(encoder.encode(UUID.randomUUID().toString()));
       // user.setPassword(encoder.encode("test"));

      //  Role = Resident;
        Set<Role> roleSet = new HashSet<>();
        Optional<Role> maybeResident = roleRepository.findById(1L);
        roleSet.add(maybeResident.get());
        user.setRoles(roleSet);
        //HashSets for the remaining fields
        user.setUserPosts(new HashSet<>());
        user.setSecond_participantInChat(new HashSet<>());
        user.setFirst_participantInChat(new HashSet<>());
        user.setMessages(new HashSet<>());
        if (userDto.getHouseId() != null) {
            Optional<House> maybeHouse = houseRepository.findById(userDto.getHouseId());
            if (maybeHouse.isPresent()) {
                user.setHouse(maybeHouse.get());
            }
        }
        user.setHasChangedPW(false);
        userRepository.save(user);
        userDto.setId(user.getId());
        //sends mail with temporary PW to user Mailadress
        EmailDetails details = new EmailDetails( user.getMail(),user.getId());
        emailService.sendMailToResetPw(details);

        return userDto;
    }

//whole userDTO bc update is a bttn next to existing user to change mail if necessary
    @Override
    public MyUserDto updateUser(MyUserDto user) {
        Optional<MyUser> maybeUser = userRepository.findById(user.getId());
        if(maybeUser.isPresent()) {
            MyUser userEntity = maybeUser.get();
            userEntity.setId(user.getId());
            userEntity.setMail(user.getMail());
            userRepository.save(userEntity);
            //new temp PW is sent to new user mail
            EmailDetails details = new EmailDetails( userEntity.getMail(),user.getId());
            emailService.sendMailToResetPw(details);

        }
        return user;
    }
//necessary to hardcode an AdminUser in HHuddleApplication
    @Override
    public CreateAdminDto createAdminUser(CreateAdminDto adminUser) throws Exception {
       if(adminUser.getId() != null){
           Optional<MyUser> maybeUser = userRepository.findById(adminUser.getId());
           if (maybeUser.isPresent()) {
              throw new Exception("User already exists.");

           }
       }
        Optional<MyUser> maybeUser = userRepository.findByUsername(adminUser.getUsername());
        if (maybeUser.isPresent()) {
            throw new Exception("User already exists.");
        }

        MyUser newUser = new MyUser();
        newUser.setMail(adminUser.getMail());
        newUser.setUsername(adminUser.getUsername());
        HashSet<Role> roles = new HashSet<>();
        Role role = new Role(2L, ROLE_PMANAGEMENT,null);
        roles.add(role);
        newUser.setRoles(roles);
        newUser.setPassword(encoder.encode(adminUser.getPassword()));
        newUser.setHouse(null);
        newUser.setHasChangedPW(adminUser.isHasChangedPW());
        newUser.setUserPosts(new HashSet<>());
        newUser.setMessages(new HashSet<>());
        newUser.setFirst_participantInChat(new HashSet<>());
        newUser.setSecond_participantInChat(new HashSet<>());

        userRepository.save(newUser);
        adminUser.setId(newUser.getId());
        return adminUser;
    }
}

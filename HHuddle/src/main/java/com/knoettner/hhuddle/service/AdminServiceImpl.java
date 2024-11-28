package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.FacilityDto;
import com.knoettner.hhuddle.dto.HouseDto;
import com.knoettner.hhuddle.dto.MyUserDto;
import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.dto.mapper.FacilityMapper;
import com.knoettner.hhuddle.dto.mapper.HouseMapper;
import com.knoettner.hhuddle.dto.mapper.MyUserMapper;
import com.knoettner.hhuddle.dto.mapper.PostMapper;
import com.knoettner.hhuddle.models.*;
import com.knoettner.hhuddle.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.knoettner.hhuddle.Category.*;
import static com.knoettner.hhuddle.UserRole.RESIDENT;

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

    @Override
    public HouseDto createHouse(HouseDto house) {
        House realHouse = houseMapper.toEntity(house);
        houseRepository.save(realHouse);
        house.setId(realHouse.getId());
        createFacilityForHouse(realHouse);
        createBoardsForHouse(realHouse.getId());
        return house;
    }

    private void createFacilityForHouse(House house) {
        Optional<House> maybeHouse = houseRepository.findById(house.getId());
        if (maybeHouse.isPresent()) {
            for (Facility facility : house.getFacilities()) {
                facility.setHouse(maybeHouse.get());
                facilityRepository.save(facility);
            }
        }
    }

    //wie bekomme ich admin/userid?
    @Override
    public Set<HouseDto> getAllHouses() {


        return Set.of();
    }

    @Override
    public void deleteHouseById(Long id) {

        houseRepository.deleteById(id);
    }

    @Override
    public void createBoardsForHouse(Long houseId) {
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


    @Override
    public PostDto createAdminPost(PostDto post) {
        Post newAdminPost = new Post();
        newAdminPost.setText(post.getText());
        newAdminPost.setCategory(FRONTPAGE);
        newAdminPost.setTitle(post.getTitle());
        newAdminPost.setTimestamp(LocalDateTime.now());
        postRepository.save(newAdminPost);
        post.setId(newAdminPost.getId());
        return post;
    }

    @Override
    public void deleteAdminPost(UserPostKey id) {
        postRepository.deleteById(id.getPostId());
    }

    @Override
    public Set<PostDto> getAdminPostsByHouseId(Long houseId) {
        Set<PostDto> allAdminPosts = new HashSet<>();
        List<Post> allPosts = postRepository.findAll();
        for (Post currentPost : allPosts) {
            if (currentPost.getUserPost().getUser().getHouse().getId() == houseId && currentPost.getCategory() == FRONTPAGE) {
                PostDto dto = postMapper.toDto(currentPost);
                allAdminPosts.add(dto);
            }

        }
        return allAdminPosts;
    }

    @Override
    public FacilityDto createFacility(FacilityDto facility) {
        Facility realFacility = facilityMapper.toEntity(facility);
        facilityRepository.save(realFacility);
        facility.setId(realFacility.getId());
        return facility;
    }


    @Override
    public FacilityDto updateFacility( FacilityDto facility) {
        facilityRepository.save(facilityMapper.toEntity(facility));
        return facility;
    }

    @Override
    public void deleteFacilityById(Long id) {
        facilityRepository.deleteById(id);
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

    //RandomPW?
    @Override
    public MyUserDto createUser(MyUserDto userDto) {
        MyUser user = userMapper.toEntity(userDto);
        //Random PW
        user.setPassword(UUID.randomUUID().toString());
        //Role = Resident
       // Role resident = new Role(1L, RESIDENT, new HashSet<>());
        //Set<Role> roleSet = new HashSet<>();
        //roleSet.add(resident);
        //user.setRoles(roleSet);
        //HashSets for the remaining fields
        user.setUserPosts(new HashSet<>());
        user.setSecond_participantInChat(new HashSet<>());
        user.setFirst_participantInChat(new HashSet<>());
        user.setMessages(new HashSet<>());
        userRepository.save(user);
        userDto.setId(user.getId());
        return userDto;
    }

    //Enough?
    @Override
    public MyUserDto updateUser(MyUserDto user) {
        userRepository.save(userMapper.toEntity(user));
        return user;
    }
}

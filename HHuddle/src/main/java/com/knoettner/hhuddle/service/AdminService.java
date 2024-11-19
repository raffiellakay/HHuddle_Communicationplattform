package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.*;
import com.knoettner.hhuddle.models.Board;
import com.knoettner.hhuddle.models.MyUser;

import java.util.Set;

public interface AdminService {

    HouseDto createHouse(HouseDto house);
    Set<HouseDto> getAllHouses();
    void deleteHousebyId(Long id);
    HouseDto updateHouse(Long id, HouseDto house);


    Board createBoardForHouse(Long houseId);
    Board addPostToBoard(Long id, Long postId);

    Set<Board> getAllBoards();

    //?????????
    Board linkBoardtoUserPosts()


    FacilityDto createFacility (FacilityDto facility);
    FacilityDto updateFacility(Long id, FacilityDto facility);
    void deleteFacilityById(Long id);

    Set<FacilityDto> getallFacilitiesfromHouse(Long houseId);

    FacilityDto addFacilityToHouse(Long houseId,Long facilityId);



    // Auch in Admin?
    RoleDto createAdminRole();
    RoleDto createResidentRole();
    RoleDto addRoleToResident(Long roleId, Long userId);
    PostDto createPost(PostDto post);
    void deletePost(UserPostKey id);
    PostDto updatePost(Long id, PostDto post);
    MyUserDto createUser(MyUserDto user);
    MyUserDto updateUser(Long id, MyUserDto user);

    MyUserDto addUserToHouse(Long HouseId, MyUserDto user);

}

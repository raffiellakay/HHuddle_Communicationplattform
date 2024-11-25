package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.*;
import com.knoettner.hhuddle.models.Board;
import com.knoettner.hhuddle.models.MyUser;

import java.util.Set;
//all methods necessary for the admin view/interactions
public interface AdminService {

//Houses
    HouseDto createHouse(HouseDto house);
    Set<HouseDto> getAllHouses();
    void deleteHouseById(Long id);
//only if needed
//    HouseDto updateHouse(Long id, HouseDto house);

//Boards for Houses - when House created necessary to create all 5 Boards for the house
    void createBoardsForHouse(Long houseId);
    Long getAdminBoardIdByHouseId(Long houseId);

//Posting for Admins
    PostDto createAdminPost(PostDto post);
    void deleteAdminPost(UserPostKey id);
    Set<PostDto> getAdminPostsByHouseId(Long houseId);
  //only if needed
    // PostDto updateAdminPost(Long id, PostDto post);


//Facilities for Houses
    FacilityDto createFacility (FacilityDto facility);
    FacilityDto updateFacility(FacilityDto facility);
    void deleteFacilityById(Long id);
    Set<FacilityDto> getAllFacilitiesByHouseId(Long houseId);

    //no addFacilityToHouse Method bc HouseId is in FacilityDTO

//Residents for House when created

    MyUserDto createUser (MyUserDto userDto);
    MyUserDto updateUser( MyUserDto user);
    //no delete necessary
    //no addFunction necessary bc HouseId is in MyUserDTO


}

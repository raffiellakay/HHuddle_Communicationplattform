package com.knoettner.hhuddle.controller;


import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.FacilityDto;
import com.knoettner.hhuddle.dto.HouseDto;
import com.knoettner.hhuddle.dto.MyUserDto;
import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/house")
    public HouseDto createHouse(@RequestBody HouseDto house) {
        return adminService.createHouse(house);
    }
/*
    Set<HouseDto> getAllHouses();
    void deleteHouseById(Long id)
    void createBoardsForHouse(Long houseId);
    Long getAdminBoardIdByHouseId(Long houseId);
    PostDto createAdminPost(PostDto post);
    void deleteAdminPost(UserPostKey id);
    Set<PostDto> getAdminPostsByHouseId(Long houseId);
    FacilityDto createFacility (FacilityDto facility);
    FacilityDto updateFacility(FacilityDto facility);
    void deleteFacilityById(Long id);
    Set<FacilityDto> getAllFacilitiesByHouseId(Long houseId);
    MyUserDto createUser (MyUserDto userDto);
    MyUserDto updateUser( MyUserDto user);
    */
}

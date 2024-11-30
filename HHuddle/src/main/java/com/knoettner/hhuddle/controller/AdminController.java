package com.knoettner.hhuddle.controller;


import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.FacilityDto;
import com.knoettner.hhuddle.dto.HouseDto;
import com.knoettner.hhuddle.dto.MyUserDto;
import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.models.Facility;
import com.knoettner.hhuddle.models.House;
import com.knoettner.hhuddle.repository.FacilityRepository;
import com.knoettner.hhuddle.repository.HouseRepository;
import com.knoettner.hhuddle.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    HouseRepository houseRepository;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/house")
    public HouseDto createHouse(@RequestBody HouseDto house) {
        return adminService.createHouse(house);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/user")
    public MyUserDto createUser(@RequestBody MyUserDto userDto) {
        return adminService.createUser(userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/post")
    PostDto createAdminPost(@RequestBody PostDto post) {
        return adminService.createAdminPost(post);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/facility")
    FacilityDto createFacility(@RequestBody FacilityDto facility) {
        return adminService.createFacility(facility);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/facility/{id}")
    void deleteFacilityById(@PathVariable("id") Long id) {
        Optional<Facility> maybeFacility = facilityRepository.findById(id);
        if (maybeFacility.isPresent()) {
            adminService.deleteFacilityById(id);
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/house/{id}")
    void deleteHouseById(@PathVariable("id") Long id ) {
        Optional<House> maybeHouse = houseRepository.findById(id);
        if (maybeHouse.isPresent()) {
            adminService.deleteHouseById(id);
        }
    }




    /*
    Set<HouseDto> getAllHouses();
    Long getAdminBoardIdByHouseId(Long houseId);
    void deleteAdminPost(UserPostKey id);
    Set<PostDto> getAdminPostsByHouseId(Long houseId);
    FacilityDto updateFacility(FacilityDto facility);
    Set<FacilityDto> getAllFacilitiesByHouseId(Long houseId);
    MyUserDto updateUser( MyUserDto user);
    */
}

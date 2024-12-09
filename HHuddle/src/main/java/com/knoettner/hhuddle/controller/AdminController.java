package com.knoettner.hhuddle.controller;


import com.knoettner.hhuddle.dto.FacilityDto;
import com.knoettner.hhuddle.dto.HouseDto;
import com.knoettner.hhuddle.dto.MyUserDto;
import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.models.Facility;
import com.knoettner.hhuddle.models.House;
import com.knoettner.hhuddle.models.Post;
import com.knoettner.hhuddle.repository.FacilityRepository;
import com.knoettner.hhuddle.repository.HouseRepository;
import com.knoettner.hhuddle.repository.PostRepository;
import com.knoettner.hhuddle.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
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
    private PostRepository postRepository;
 //
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/house")
    @PreAuthorize("hasRole('P_MANAGEMENT')")
    public HouseDto createHouse(@RequestBody HouseDto house) {
        return adminService.createHouse(house);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/user")
    public MyUserDto createUser(@RequestBody MyUserDto userDto) {
        return adminService.createUser(userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/adminpost")
    @PreAuthorize("hasRole('P_MANAGEMENT')")
    PostDto createAdminPost(@RequestBody PostDto post) {
        return adminService.createAdminPost(post);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/facility")
    @PreAuthorize("hasRole('P_MANAGEMENT')")
    FacilityDto createFacility(@RequestBody FacilityDto facility) {
        return adminService.createFacility(facility);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/facility/{id}")
    @PreAuthorize("hasRole('P_MANAGEMENT')")
    void deleteFacilityById(@PathVariable("id") Long id) {
        adminService.deleteFacilityById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/house/{id}")
    @PreAuthorize("hasRole('P_MANAGEMENT')")
    void deleteHouseById(@PathVariable("id") Long id ) {
        adminService.deleteHouseById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/adminpost/{id}")
    @PreAuthorize("hasRole('P_MANAGEMENT')")
    void deleteAdminPost(@PathVariable("id") Long postId) {
        adminService.deleteAdminPost(postId);
    }
//
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/houses")
  //  @PreAuthorize("hasRole('P_MANAGEMENT')")
    Set<HouseDto> getAllHouses() {
        return adminService.getAllHouses();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/house/{id}")
    @PreAuthorize("hasRole('P_MANAGEMENT')")
    HouseDto getHouseById (@PathVariable("id")  Long houseId) {
       return adminService.getHouseById(houseId);
    }
    //
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/house/{id}/adminposts")
    @PreAuthorize("hasRole('P_MANAGEMENT')")
    Set<PostDto> getAdminPostsByHouseId(@PathVariable("id") Long houseId) {
        return adminService.getAdminPostsByHouseId(houseId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/house/{id}/adminboard")
    @PreAuthorize("hasRole('P_MANAGEMENT')")
    Long getAdminBoardIdByHouseId(@PathVariable("id") Long houseId) {
        return adminService.getAdminBoardIdByHouseId(houseId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("house/{id}/facility")
    @PreAuthorize("hasRole('P_MANAGEMENT')")
    Set<FacilityDto> getAllFacilitiesByHouseId(@PathVariable("id") Long houseId) {
        return adminService.getAllFacilitiesByHouseId(houseId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/facility")
    @PreAuthorize("hasRole('P_MANAGEMENT')")
    FacilityDto updateFacility(@RequestBody FacilityDto facility) {
    return adminService.updateFacility(facility);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/user")
    @PreAuthorize("hasRole('P_MANAGEMENT')")
    MyUserDto updateUser(@RequestBody MyUserDto user) {
    return adminService.updateUser(user);
    }


}

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    public HouseDto createHouse(@RequestBody HouseDto house) {
        return adminService.createHouse(house);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/user")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    public MyUserDto createUser(@RequestBody MyUserDto userDto) {
        return adminService.createUser(userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/adminpost")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    PostDto createAdminPost(@RequestBody PostDto post) {
        return adminService.createAdminPost(post);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/facility")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    FacilityDto createFacility(@RequestBody FacilityDto facility) {
        return adminService.createFacility(facility);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/facility/{id}")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    void deleteFacilityById(@PathVariable("id") Long id) {
        adminService.deleteFacilityById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/house/{id}")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    void deleteHouseById(@PathVariable("id") Long id ) {
        adminService.deleteHouseById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/adminpost/{id}")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    void deleteAdminPost(@PathVariable("id") Long postId) {
        adminService.deleteAdminPost(postId);
    }
//
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/houses")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    Set<HouseDto> getAllHouses() {
        return adminService.getAllHouses();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/house/{id}")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    HouseDto getHouseById (@PathVariable("id")  Long houseId) {
       return adminService.getHouseById(houseId);
    }
    //
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/house/{id}/adminposts")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    Set<PostDto> getAdminPostsByHouseId(@PathVariable("id") Long houseId) {
        return adminService.getAdminPostsByHouseId(houseId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/house/{id}/adminboard")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    Long getAdminBoardIdByHouseId(@PathVariable("id") Long houseId) {
        return adminService.getAdminBoardIdByHouseId(houseId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("house/{id}/facility")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    Set<FacilityDto> getAllFacilitiesByHouseId(@PathVariable("id") Long houseId) {
        return adminService.getAllFacilitiesByHouseId(houseId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/facility")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    FacilityDto updateFacility(@RequestBody FacilityDto facility) {
    return adminService.updateFacility(facility);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/user")
    // Use hasRole for role-based access control
    @PreAuthorize("hasRole('PMANAGEMENT')")
    MyUserDto updateUser(@RequestBody MyUserDto user) {
    return adminService.updateUser(user);
    }


}

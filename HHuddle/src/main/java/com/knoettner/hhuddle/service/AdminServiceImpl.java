package com.knoettner.hhuddle.service;

import com.knoettner.hhuddle.UserPostKey;
import com.knoettner.hhuddle.dto.FacilityDto;
import com.knoettner.hhuddle.dto.HouseDto;
import com.knoettner.hhuddle.dto.MyUserDto;
import com.knoettner.hhuddle.dto.PostDto;

import java.util.Set;

public class AdminServiceImpl implements AdminService {
    @Override
    public HouseDto createHouse(HouseDto house) {
        return null;
    }

    @Override
    public Set<HouseDto> getAllHouses() {
        return Set.of();
    }

    @Override
    public void deleteHouseById(Long id) {

    }

    @Override
    public void createBoardsForHouse() {

    }

    @Override
    public Long getAdminBoardIdByHouseId(Long houseId) {
        return 0L;
    }

    @Override
    public PostDto createAdminPost(PostDto post) {
        return null;
    }

    @Override
    public void deleteAdminPost(UserPostKey id) {

    }

    @Override
    public Set<PostDto> getAdminPostsByHouseId(Long houseId) {
        return Set.of();
    }

    @Override
    public FacilityDto createFacility(FacilityDto facility) {
        return null;
    }

    @Override
    public FacilityDto updateFacility(Long id, FacilityDto facility) {
        return null;
    }

    @Override
    public void deleteFacilityById(Long id) {

    }

    @Override
    public Set<FacilityDto> getAllFacilitiesByHouseId(Long houseId) {
        return Set.of();
    }

    @Override
    public MyUserDto createUser(MyUserDto userDto) {
        return null;
    }

    @Override
    public MyUserDto updateUser(Long id, MyUserDto user) {
        return null;
    }
}

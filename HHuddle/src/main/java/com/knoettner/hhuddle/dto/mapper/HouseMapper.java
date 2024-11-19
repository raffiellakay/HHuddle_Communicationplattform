package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.dto.HouseDto;
import com.knoettner.hhuddle.models.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;


@Component
public class HouseMapper {
    @Autowired
    MyUserMapper myUserMapper;
    FacilityMapper facilityMapper;

    public House toEntity(HouseDto houseDto) {
        House house = new House();
        house.setId(houseDto.getId());
        house.setAdress(houseDto.getAdress());
        if (houseDto.getResidents() != null && !houseDto.getResidents().isEmpty()){
        house.setResidents(houseDto.getResidents().stream().map(e -> myUserMapper.toEntity(e)).collect(Collectors.toSet()));
        }else {
            house.setResidents(Collections.emptySet());
        }
        house.setFacilities(houseDto.getFacilities().stream().map(e -> facilityMapper.toEntity(e)).collect(Collectors.toSet()));//Hat ein Haus immer Facilities?

        return house;
    }

    public HouseDto todDto(House house) {
        HouseDto houseDto = new HouseDto();
        houseDto.setId(house.getId());
        houseDto.setAdress(house.getAdress());
        if (house.getResidents() != null && !house.getResidents().isEmpty()){
        houseDto.setResidents(house.getResidents().stream().map(e -> myUserMapper.toDto(e)).collect(Collectors.toSet()));
        }else {
            houseDto.setResidents(Collections.emptySet());
        }
        houseDto.setFacilities(house.getFacilities().stream().map(e -> facilityMapper.toDto(e)).collect(Collectors.toSet()));


        return houseDto;
    }

}

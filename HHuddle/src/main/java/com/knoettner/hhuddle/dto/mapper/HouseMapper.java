package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.dto.HouseDto;
import com.knoettner.hhuddle.models.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//nicht fertig
@Component
public class HouseMapper {
    @Autowired
    UserMapper userMapper;

    public House toEntity(HouseDto houseDto) {
        House house = new House();
        house.setId(houseDto.getId());
        house.setAdress(houseDto.getAdress());


        return house;
    }
}

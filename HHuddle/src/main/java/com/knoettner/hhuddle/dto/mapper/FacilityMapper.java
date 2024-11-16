package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.dto.FacilityDto;
import com.knoettner.hhuddle.models.Facility;
import com.knoettner.hhuddle.models.Post;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Component
public class FacilityMapper {

    public FacilityDto toDto(Facility facility) {
        FacilityDto newFacilityDto = new FacilityDto();
        newFacilityDto.setId(facility.getId());
        newFacilityDto.setDescription(facility.getDescription());
        newFacilityDto.setType(facility.getType());
        newFacilityDto.setHouseId(facility.getHouse().getId());
        return newFacilityDto;
    }

    public Facility toEntity(FacilityDto facilityDto) {
        Facility newFacility = new Facility();
        newFacility.setId(facilityDto.getId());
        newFacility.setType(facilityDto.getType());
        newFacility.setDescription(facilityDto.getDescription());
        // unfertig!
        return newFacility;


    }
}
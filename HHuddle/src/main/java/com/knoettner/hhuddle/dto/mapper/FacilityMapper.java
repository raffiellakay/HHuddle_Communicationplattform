package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.dto.FacilityDto;
import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.models.Facility;
import com.knoettner.hhuddle.models.House;
import com.knoettner.hhuddle.models.Post;
import com.knoettner.hhuddle.repository.HouseRepository;
import com.knoettner.hhuddle.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FacilityMapper {
    @Autowired
    HouseRepository houseRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostMapper  postMapper;

    public FacilityDto toDto(Facility facility) {
        FacilityDto newFacilityDto = new FacilityDto();
        newFacilityDto.setId(facility.getId());
        newFacilityDto.setDescription(facility.getDescription());
        newFacilityDto.setType(facility.getType());
        newFacilityDto.setHouseId(facility.getHouse().getId());
        if (facility.getPosts() != null) {
            Set<PostDto> postDtos = new HashSet<>();
            for (Post currentPost : facility.getPosts()) {
                PostDto postDto = postMapper.toDto(currentPost);
                postDtos.add(postDto);
            }
            newFacilityDto.setPostDtos(postDtos);
            }

        return newFacilityDto;
    }

    public Facility toEntity(FacilityDto facilityDto) {
        Facility newFacility = new Facility();
        newFacility.setId(facilityDto.getId());
        newFacility.setType(facilityDto.getType());
        newFacility.setDescription(facilityDto.getDescription());
        if(facilityDto.getHouseId() != null) {
            Optional<House> optionalHouse = houseRepository.findById(facilityDto.getHouseId());
            if (optionalHouse.isPresent()) {
                newFacility.setHouse(optionalHouse.get());
            }
        }
        if(facilityDto.getPostDtos() != null) {
            Set<Post> posts = new HashSet<>();
            for (PostDto postDto : facilityDto.getPostDtos()) {
                Post post = postMapper.toEntity(postDto);
                posts.add(post);
            }
            newFacility.setPosts(posts);
        }
        return newFacility;


    }
}
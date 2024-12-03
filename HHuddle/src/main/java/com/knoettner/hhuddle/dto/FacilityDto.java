package com.knoettner.hhuddle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor


public class FacilityDto {
    private Long id;
    private String type;
    private String description;

    private Long houseId;
    private Set<RequestPostDto> requestPostDtos;
}

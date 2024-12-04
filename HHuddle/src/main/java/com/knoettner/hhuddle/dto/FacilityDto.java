package com.knoettner.hhuddle.dto;

import com.knoettner.hhuddle.models.House;
import com.knoettner.hhuddle.models.Post;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor


public class FacilityDto {
    private Long id;
    private String type;
    private String description;

    private Long houseId;
    private Set<PostDto> postDtos;
}

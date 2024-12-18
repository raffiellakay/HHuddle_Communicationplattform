package com.knoettner.hhuddle.dto;

import com.knoettner.hhuddle.Category;
import com.knoettner.hhuddle.models.House;
import com.knoettner.hhuddle.models.UserPost;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String category;
    private Long houseId;
}

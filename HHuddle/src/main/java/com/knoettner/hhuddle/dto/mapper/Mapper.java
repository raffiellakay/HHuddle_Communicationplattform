package com.knoettner.hhuddle.dto.mapper;

public interface Mapper <Dto,Entity>{
    Entity toEntity(Dto dto);
    Dto toDto(Entity entity);
}
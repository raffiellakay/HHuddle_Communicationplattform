package com.knoettner.hhuddle.dto.mapper;

import com.knoettner.hhuddle.dto.BoardDto;
import com.knoettner.hhuddle.models.Board;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {

    public BoardDto toDto (Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setCategory(board.getCategory().toString());
        boardDto.setHouseId(board.getHouse().getId());
        return boardDto;
    }

    public Board toEntity(BoardDto boardDto){
       return null; //TODO toEntity
    }
}

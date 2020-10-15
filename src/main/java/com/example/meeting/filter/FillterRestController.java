package com.example.meeting.filter;

import com.example.meeting.board.Board;
import com.example.meeting.board.BoardRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class FillterRestController {

    private BoardRepository boardRepository;

    public FillterRestController(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @GetMapping(value ="/filter")
    public List<BoardDto> getFilterAge(@RequestParam("age") int age){
        System.out.println(age);
        List<Board> boards = boardRepository.findByAgeLessThanEqualAndAgeGreaterThanEqual(age+3, age-3);


        List<BoardDto> boardDtos = new ArrayList<BoardDto>();

        for(int i=0; i<boards.size(); ++i){
            BoardDto boardDto = new BoardDto();
            boardDto.setIdx(boards.get(i).getIdx());
            boardDto.setTitle(boards.get(i).getTitle());
            boardDto.setImg1(boards.get(i).getImg1());
            boardDto.setImg2(boards.get(i).getImg2());
            boardDto.setImg3(boards.get(i).getImg3());
            boardDto.setKeyword(boards.get(i).getKeyword());
            boardDto.setLocation(boards.get(i).getLocation());
            boardDto.setNum_type(boards.get(i).getNum_type());
            boardDto.setAge(boards.get(i).getAge());
            boardDto.setGender(boards.get(i).getGender());
            boardDto.setCreatedDate(boards.get(i).getCreatedDate());
            boardDto.setUpdatedDate(boards.get(i).getUpdatedDate());

            boardDtos.add(boardDto);
        }

        return boardDtos;


    }

}

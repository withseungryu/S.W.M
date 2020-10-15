package com.example.meeting.board;

import com.example.meeting.board.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.*;




@RepositoryRestController
public class BoardRestController {

    private BoardRepository boardRepository;


    public BoardRestController(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }





    @PostMapping(value = "/boards"  )
    public ResponseEntity<?> postBoard( @RequestParam int age ) throws IOException {

        System.out.println(age);

        return new ResponseEntity<>("성공", HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putBoard(@PathVariable("idx")Long idx, @RequestBody Board board){
        Board persistBoard = boardRepository.getOne(idx);
        persistBoard.update(board);
        boardRepository.save(persistBoard);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteBoard(@PathVariable("idx")Long idx){
        boardRepository.deleteById(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }









}

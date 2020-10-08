package com.example.meeting.board;

import com.example.meeting.board.BoardRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
public class BoardRestController {

    private BoardRepository boardRepository;


    public BoardRestController(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @PostMapping
    public ResponseEntity<?> postBoard(@RequestBody Board board){
        board.setImgs(board.getImg1(), board.getImg2(), board.getImg3());
        boardRepository.save(board);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }


}

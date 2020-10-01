package com.example.meeting.board;

import com.example.meeting.board.BoardRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public class BoardRestController {

    private BoardRepository boardRepository;


    public BoardRestController(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }
}

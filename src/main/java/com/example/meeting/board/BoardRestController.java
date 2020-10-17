package com.example.meeting.board;

import com.example.meeting.board.BoardRepository;
import com.example.meeting.fileupload.S3Uploader;
import com.example.meeting.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.Map;


@RepositoryRestController
public class BoardRestController {

    private BoardRepository boardRepository;
    private UserRepository userRepository;

    public BoardRestController(BoardRepository boardRepository, UserRepository userRepository){
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/boards" )
    public ResponseEntity<?> postBoard(@RequestParam("data") Map<String, Object> data) throws IOException {

        String title = data.get("title").toString();
        String keyword = data.get("keyword").toString();
        String location = data.get("location").toString();
        String num_type = data.get("num_type").toString();
        String age = data.get("age").toString();
        String gender = data.get("gender").toString();

        System.out.println(title + " " + keyword + " " + location + " " + num_type + " " + age + " " + gender );

        Board board = new Board();
        board.setTitle(title);
        board.setKeyword(keyword);
        board.setImg1("/static/" + title+"1.jpg");
        board.setImg2("/static/" + title+"2.jpg");
        board.setImg3("/static/" + title+"3.jpg");
        board.setLocation(location);
        board.setNum_type(num_type);
        board.setAge(Integer.parseInt(age));
        board.setGender(gender);
        board.setUser(userRepository.getOne(1L));
        board.setCreatedDateNow();
        board.setUpdatedDateNow();

        boardRepository.save(board);



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

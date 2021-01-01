package com.example.meeting.controller;

import com.example.meeting.entity.Board;
import com.example.meeting.dao.BoardRepository;
import com.example.meeting.dto.board.Answer;
import com.example.meeting.dto.board.BoardDto;
import com.example.meeting.entity.Bookmark;
import com.example.meeting.config.S3Uploader;
import com.example.meeting.entity.Matched;
import com.example.meeting.dao.MatchedRepository;
import com.example.meeting.entity.User;
import com.example.meeting.dao.UserRepository;
import com.example.meeting.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.example.meeting.service.BoardService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/boards")
public class BoardRestController {

    private BoardService boardService;
    private BoardRepository boardRepository;
    private UserRepository userRepository;
    private MatchedRepository matchedRepository;
    private S3Uploader s3Uploader;

    @GetMapping
    public ResponseEntity<List<BoardDto>> getBoard(Pageable pageable,
                                                   @RequestParam(value = "location1", required = false) String location1,
                                                   @RequestParam(value = "location2", required = false) String location2,
                                                   @RequestParam(value ="num_type" ,required = false) String num_type,
                                                   @RequestParam(value ="age", required = false) String age,
                                                   @RequestParam(value = "userId", required = false) Long userId,
                                                   @RequestParam(value="gender", required = false) String gender,
                                                   @RequestParam(value="date", required=false) String pdate
    ) throws IOException, ParseException {

        List<BoardDto> adaBoard = boardService.getBoards(pageable, location1, location2, num_type, age, userId, gender, pdate);
        return new ResponseEntity<>(adaBoard, HttpStatus.OK);

    }

    @PostMapping
    public @ResponseBody ResponseEntity<Answer> boardUpload(@RequestParam(value = "img1") MultipartFile img1,
                                                            @RequestParam(value="img2") MultipartFile img2,
                                                            @RequestParam(value = "img3") MultipartFile img3,
                                                            @RequestParam("title") @RequestBody String ptitle,
                                                            @RequestParam("location1") @RequestBody String plocation1,
                                                            @RequestParam("location2") @RequestBody String plocation2,
                                                            @RequestParam("num_type") @RequestBody String pnum_type,
                                                            @RequestParam("gender") @RequestBody String pgender,
                                                            @RequestParam(value="tag1", required = false) @RequestBody String ptag1,
                                                            @RequestParam(value="tag2", required = false) @RequestBody String ptag2,
                                                            @RequestParam(value="tag3", required = false) @RequestBody String ptag3,
                                                            @RequestParam("average_age") @RequestBody String page,
                                                            @RequestParam(value= "date", required = false) @RequestBody String pdate,
                                                            @RequestParam(value = "user", required = false) @RequestBody String puser
    ) throws IOException, ParseException {

        try {
            boardService.uploadBoard(img1, img2, img3, ptitle, plocation1, plocation2, pnum_type,
                    pgender, ptag1, ptag2, ptag3, page, pdate, puser);
        }catch(Exception e){
            Answer ans = new Answer();
            ans.setAnswer(200, "Failed");
            return new ResponseEntity<>(ans, HttpStatus.CREATED);
        }

        Answer ans = new Answer();
        ans.setAnswer(200, "Success");

        return new ResponseEntity<>(ans, HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}/{userId}")
    public ResponseEntity<BoardDto> putBoard(@PathVariable("boardId")Long boardId, @PathVariable("userId") Long userId){

        BoardDto boardDto = boardService.putBoard(boardId, userId);

        return new ResponseEntity<>(boardDto, HttpStatus.OK);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putBoard(@PathVariable("idx")Long idx, @RequestBody Board board){
        Board persistBoard = boardRepository.getOne(idx);
        persistBoard.update(board);
        boardRepository.save(persistBoard);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<Answer> deleteBoard(@PathVariable("idx")Long idx){
        boardRepository.deleteById(idx);
        Answer ans = new Answer();
        ans.setAnswer(200, "success");
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    @GetMapping("/rec/time/{idx}")
    public ResponseEntity<List<Board>> recTimeBoard( @PathVariable("idx") Long idx){

        List<Board> boards = boardService.recTimeBoard(idx);

        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping("/rec/location/{idx}")
    public ResponseEntity<List<Board>> recLocationBoard(@PathVariable("idx") Long idx){

        List<Board> boards = boardService.recLocationBoard(idx);

        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

}

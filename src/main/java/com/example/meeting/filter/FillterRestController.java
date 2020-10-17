package com.example.meeting.filter;

import com.example.meeting.board.Board;
import com.example.meeting.board.BoardRepository;

import com.example.meeting.fileupload.S3Uploader;
import com.example.meeting.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class FillterRestController {

    private BoardRepository boardRepository;
    private UserRepository userRepository;
    public FillterRestController(BoardRepository boardRepository, UserRepository userRepository){
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value ="/filter")
    public List<BoardDto> getFilterAge(@RequestParam(value = "location", required = false) String location, @RequestParam(value ="num_type" ,required = false) String num_type, @RequestParam(value ="age", required = false) String age){
        System.out.println(location + " " + num_type + " " + age );

        System.out.println(location.getClass().getName());
        List<Board> boards = new ArrayList<>();

        if(location.equals("상관없음") && num_type.equals("상관없음") && age.equals("상관없음")){
            System.out.println("1");
             boards = boardRepository.findAll();
        }else if(location.equals("상관없음") && num_type.equals("상관없음")){
            System.out.println("2");
            int age1 = Integer.parseInt(age) +3;
            int age2 = Integer.parseInt(age) -3;
             boards = boardRepository.getList3(age1, age2);
        }else if(location.equals("상관없음") && age.equals("상관없음")){
            System.out.println("3");
            boards = boardRepository.getList2(num_type);
        }else if(age.equals("상관없음") && num_type.equals("상관없음")){
            System.out.println("4");
            boards = boardRepository.getList1(location);
        }else if(location.equals("상관없음")){
            System.out.println("5");
            int age1 = Integer.parseInt(age) +3;
            int age2 = Integer.parseInt(age) -3;
            boards = boardRepository.getList6(num_type, age1, age2);
        }else if(num_type.equals("상관없음")){
            System.out.println("6");
            int age1 = Integer.parseInt(age) +3;
            int age2 = Integer.parseInt(age) -3;
            boards = boardRepository.getList5(location, age1,age2);

        }else if(age.equals("상관없음")){
            System.out.println("7");
            boards = boardRepository.getList4(location, num_type);
        }else{
            System.out.println("8");
            int age1 = Integer.parseInt(age) +3;
            int age2 = Integer.parseInt(age) -3;
            boards = boardRepository.getList7(location, num_type, age1, age2);
        }


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
            boardDto.setAge(Integer.toString(boards.get(i).getAge()));
            boardDto.setGender(boards.get(i).getGender());
            boardDto.setCreatedDate(boards.get(i).getCreatedDate().toString());
            boardDto.setUpdatedDate(boards.get(i).getUpdatedDate().toString());

            boardDtos.add(boardDto);
        }

        return boardDtos;


    }


    S3Uploader s3Service = new S3Uploader();
//    @PostMapping("/api/boards/upload")
//    public String boardUpload(@RequestParam("img1") MultipartFile img1) throws IOException {
//
//          //Test


//        return "성공";
//
//    }

}

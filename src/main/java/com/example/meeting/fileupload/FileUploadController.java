package com.example.meeting.fileupload;


import com.example.meeting.board.Board;
import com.example.meeting.board.BoardRepository;
import com.example.meeting.filter.BoardDto;
import com.example.meeting.user.User;
import com.example.meeting.user.UserDto;
import com.example.meeting.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class FileUploadController {


    private BoardRepository boardRepository;
    private UserRepository userRepository;


    private S3Uploader s3Service;
    @PostMapping("/api/boards/upload")
    public @ResponseBody String boardUpload(@RequestParam(value = "img1") MultipartFile img1,
                                                            @RequestParam(value="img2") MultipartFile img2,
                                                            @RequestParam(value = "img3") MultipartFile img3,
                                                            @RequestParam("title") @RequestBody String ptitle,
                                                            @RequestParam("keyword") @RequestBody String pkeyword,
                                                            @RequestParam("location") @RequestBody String plocation,
                                                            @RequestParam("num_type") @RequestBody String pnum_type,
                                                            @RequestParam("gender") @RequestBody String pgender,
                                                            @RequestParam("age") @RequestBody String page,
                                                            @RequestParam(value = "user", required = false) @RequestBody String puser
    )  throws IOException {




        String title = ptitle;
        String keyword = pkeyword;
        String location = plocation;
        String num_type = pnum_type;
        String age = page;  //안보내줘도 될듯
        String gender = pgender; //안보내줘도 될듯

        ///게시판 추가 부분
        Integer latest_data = boardRepository.test();

        if(latest_data == null){
            latest_data = 0;
        }




        Board board = new Board();
        board.setTitle(title);
        board.setKeyword(keyword);
        board.setImg1("https://swmbucket.s3.ap-northeast-2.amazonaws.com/static/" + "board_img_" + Integer.toString(latest_data+1) + "_1.jpg");
        board.setImg2("https://swmbucket.s3.ap-northeast-2.amazonaws.com/static/" + "board_img_" + Integer.toString(latest_data+1) + "_2.jpg");
        board.setImg3("https://swmbucket.s3.ap-northeast-2.amazonaws.com/static/" + "board_img_" + Integer.toString(latest_data+1) + "_3.jpg");
        board.setLocation(location);
        board.setNum_type(num_type);
        board.setAge(Integer.parseInt(age));
        board.setGender(gender);
        board.setUser(userRepository.getOne(1L));
        board.setCreatedDateNow();
        board.setUpdatedDateNow();

        boardRepository.save(board);





        /// 이미지 추가 부분

        int latest_id = boardRepository.test();
        System.out.println(latest_id);
        String rPath1 = s3Service.upload(img1, "board_img_" + Integer.toString(latest_id) + "_1.jpg" );
        String rPath2 = s3Service.upload(img2, "board_img_" + Integer.toString(latest_id) + "_2.jpg");
        String rPath3 = s3Service.upload(img3, "board_img_" + Integer.toString(latest_id) + "_3.jpg");
        System.out.println(rPath1);
        System.out.println(rPath2);
        System.out.println(rPath3);

        return "성공";


    }

    @PostMapping("/api/users/{id}/upload")
    public @ResponseBody ResponseEntity<String> boardUpload(@PathVariable("id") Long idx, @RequestParam(value = "img1") MultipartFile img1)  throws IOException {

        return new ResponseEntity<>(Long.toString(idx), HttpStatus.OK);
    }

}

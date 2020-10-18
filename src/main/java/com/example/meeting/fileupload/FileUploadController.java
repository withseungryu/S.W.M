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


    private S3Uploader s3Service;
    @PostMapping("/api/boards/upload")
    public @ResponseBody ResponseEntity<String> boardUpload(@RequestParam(value = "img1") MultipartFile img1, @RequestParam(value="img2") MultipartFile img2, @RequestParam(value = "img3") MultipartFile img3)  throws IOException {
//        System.out.println(data);
//        System.out.println(data.get("age"));
//        System.out.println(data.get("location"));
        int latest_id = boardRepository.test();
        System.out.println(latest_id);
        String rPath1 = s3Service.upload(img1, "board_img_" + Integer.toString(latest_id) + "_1.jpg" );
        String rPath2 = s3Service.upload(img2, "board_img_" + Integer.toString(latest_id) + "_2.jpg");
        String rPath3 = s3Service.upload(img3, "board_img_" + Integer.toString(latest_id) + "_3.jpg");
        System.out.println(rPath1);
        System.out.println(rPath2);
        System.out.println(rPath3);
        return new ResponseEntity<>("rPath", HttpStatus.OK);


    }

    @PostMapping("/api/users/{id}/upload")
    public @ResponseBody ResponseEntity<String> boardUpload(@PathVariable("id") Long idx, @RequestParam(value = "img1") MultipartFile img1)  throws IOException {

        return new ResponseEntity<>(Long.toString(idx), HttpStatus.OK);
    }

}

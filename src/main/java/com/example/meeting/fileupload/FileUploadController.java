package com.example.meeting.fileupload;


import com.example.meeting.filter.BoardDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class FileUploadController {


        private S3Uploader s3Service = new S3Uploader();


        @PostMapping("/api/users/upload")
        public String userUpload(@RequestParam("file") MultipartFile file) throws IOException {


            return s3Service.upload(file); // S3 bucket의 static/ 폴더를 지정한 것.


        }
//
        @PostMapping("/api/boards/upload")
        public String boardUpload(@RequestParam("img1") MultipartFile img1, @RequestParam("img2") MultipartFile img2, @RequestParam("img3") MultipartFile img3, @RequestParam("data") BoardDto bto) throws IOException {
            s3Service.upload(img1); // S3 bucket의 static/ 폴더를 지정한 것.
            s3Service.upload(img2); // S3 bucket의 static/ 폴더를 지정한 것.
            s3Service.upload(img3); // S3 bucket의 static/ 폴더를 지정한 것.
            return "성공";

        }

}

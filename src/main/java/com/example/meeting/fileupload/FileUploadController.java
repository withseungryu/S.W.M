package com.example.meeting.fileupload;


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

@RestController
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @PostMapping("/img/upload")
    public @ResponseBody
    String uploadFile(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) throws IllegalStateException, IOException {
        Arrays.asList(files)
                .stream()
                .forEach(file -> {
                    try {
                        fileUploadService.uploadFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            return "성공";
    }

}

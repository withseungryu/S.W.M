package com.example.meeting.fileupload;


import java.io.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileUploadService {

    public void uploadFile(MultipartFile file) throws IllegalStateException, IOException {
        file.transferTo(new File("C:\\Users\\alstm\\Desktop\\" + file.getOriginalFilename()));
//        for(MultipartFile file: files) {
//            file.transferTo(new File("C:\\Users\\alstm\\Desktop\\" + file.getOriginalFilename()));
//        }
    }
}

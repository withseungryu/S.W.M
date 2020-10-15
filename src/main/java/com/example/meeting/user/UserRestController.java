package com.example.meeting.user;


import com.example.meeting.fileupload.FileUploadService;
import com.example.meeting.kakao_oauth.OAuthToken;
import com.example.meeting.kakao_oauth.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@RestController
public class UserRestController {
    private UserRepository userRepository;


    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody
    String kakaoCallback(String code) { //@ResponseBody를 붙이는 이유 : Data를 리턴해주는 컨트롤러 간ㄷ된다.
        OAuth oauth= new OAuth(code);

        OAuthToken oauthToken = oauth.getOauthToken();
//        oauth.deleteProfile(oauthToken);
        oauth.getProfile(oauthToken, userRepository);

        return "{code: 0001, message: \"성공\"}";
    }



//    @GetMapping("/test/user1")
//    public @ResponseBody String testtt(@RequestParam(value="email") String email, @RequestParam(value="password") String password){
//        return email + "안녕";
//
//    }

    @PostMapping("/users/login")
    public @ResponseBody
    String testTest(@RequestBody UserTest tmp){
        User user = userRepository.findByEmail(tmp.getEmail());
        //비밀번호도 저장한 후 만들어주자
        if(user == null){
            return "실패";
        }else{
            return "성공";
        }

    }



    @PostMapping("/users/profile")
    public @ResponseBody
    String uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile files) throws IllegalStateException, IOException {

        return "성공";
    }


}

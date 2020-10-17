package com.example.meeting.user;


import com.example.meeting.fileupload.S3Uploader;
import com.example.meeting.kakao_oauth.OAuth;
import com.example.meeting.kakao_oauth.OAuthToken;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
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

    @PostMapping("api/users/login")
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
    String uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile files, @RequestParam("data") Map<String, Object> data) throws IllegalStateException, IOException {

        return "성공";
    }


    private S3Uploader s3Service;

//    @PostMapping("/api/users/upload")
//    public ResponseEntity<String> userUpload(@RequestParam("file") MultipartFile file, @RequestParam("data") Map<String, Object> data) throws IOException {
//
//
//        UserDto userDto = new UserDto();
//        userDto.setUser_id(data.get("user_id").toString());
//        userDto.setNickname(data.get("nickname").toString());
//
//        User persistUser = userRepository.getOne(Long.parseLong(userDto.getUser_id()));
//
//        persistUser.setImg(s3Service.upload(file, userDto.getUser_id()));
//        persistUser.setNickName(userDto.getNickname());
//
//        userRepository.save(persistUser);
//
//        return new ResponseEntity<>("성공", HttpStatus.OK); // S3 bucket의 static/ 폴더를 지정한 것.
//    }


}

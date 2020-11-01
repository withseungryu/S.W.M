package com.example.meeting.user;


import com.example.meeting.board.Board;
import com.example.meeting.fileupload.S3Uploader;
import com.example.meeting.kakao_oauth.OAuth;
import com.example.meeting.kakao_oauth.OAuthToken;

import com.sun.net.httpserver.Authenticator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
public class UserRestController {
    private UserRepository userRepository;
    private S3Uploader s3Uploader;

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody
    String kakaoCallback(String code) { //@ResponseBody를 붙이는 이유 : Data를 리턴해주는 컨트롤러 간ㄷ된다.
        OAuth oauth= new OAuth(code);

        OAuthToken oauthToken = oauth.getOauthToken();
//        oauth.deleteProfile(oauthToken);
        oauth.deleteProfile(oauthToken);

        return "{code: 0001, message: \"성공\"}";
    }

    @PostMapping("api/users/login")
    public @ResponseBody
    ResponseEntity<CheckAnswer> checkLogin(@RequestBody LoginEmail loginEmail){


        User user = userRepository.findByEmail(loginEmail.getEmail());
        //비밀번호도 저장한 후 만들어주자
        CheckAnswer ans = new CheckAnswer();
        if(user == null){
            ans.setCheckAnswer(200, "Success", false, 0L);
            return new ResponseEntity<>(ans, HttpStatus.OK);
        }else{
            ans.setCheckAnswer(200, "Success", true, user.getIdx());
            return new ResponseEntity<>(ans, HttpStatus.OK);
        }
    }

    @GetMapping("api/users/{idx}")
    public ResponseEntity<User> putBoard(@PathVariable("idx") Long idx){
        User user = userRepository.findByIdx(idx);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("api/users")
    public @ResponseBody
    ResponseEntity uploadFile(@RequestParam(value = "img") MultipartFile img, @RequestParam("email") String email, @RequestParam("nickName") @RequestBody  String nickName, @RequestParam("age") @RequestBody String age,  @RequestParam("location") @RequestBody String location,  @RequestParam("kakao_id") @RequestBody String kakao_id ) throws IllegalStateException, IOException {

        System.out.println(email);
        System.out.println(nickName + " " +  age);


        User user = new User();

        Answer as = new Answer();
        as.setAnswer(400, "Fail", 0L);

        user.setEmail(email);
        user.setImg("https://swmbucket.s3.ap-northeast-2.amazonaws.com/static/" + "user_img_" + email+ ".jpg");
        user.setNickName(nickName);
        user.setAge(age);
        user.setLocation(location);
        user.setKakao_id(kakao_id);

        userRepository.save(user);

        String uPath = s3Uploader.upload(img, "user_img_" + email + ".jpg" );
        System.out.println(uPath);

        Long idx = userRepository.getFromEmail(email);

        Answer as2 = new Answer();
        as2.setAnswer(200, "Success", idx);

        return new ResponseEntity<>(as2,HttpStatus.CREATED);

    }




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

package com.example.meeting.controller;

import com.example.meeting.dao.UserRepository;
import com.example.meeting.entity.User;
import com.example.meeting.config.S3Uploader;
import com.example.meeting.auth.jwt.JwtService;
import com.example.meeting.dto.user.Answer;
import com.example.meeting.dto.user.CheckAnswer;
import com.example.meeting.dto.user.LoginEmail;
import com.example.meeting.dto.user.NormalAnswer;
import com.example.meeting.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@AllArgsConstructor
@RestController
@Slf4j
public class UserRestController {
    private UserRepository userRepository;
    private S3Uploader s3Uploader;
    private UserService userService;
    private JwtService jwtService;
    /*
    kakao login api를 쓸 경우 활성화하자!
     */
//    @GetMapping("/auth/kakao/callback")
//    public @ResponseBody
//    String kakaoCallback(String code) { //@ResponseBody를 붙이는 이유 : Data를 리턴해주는 컨트롤러 간ㄷ된다.
//        OAuth oauth= new OAuth(code);
//
//        OAuthToken oauthToken = oauth.getOauthToken();
////        oauth.deleteProfile(oauthToken);
//        oauth.deleteProfile(oauthToken);
//
//        return "{code: 0001, message: \"성공\"}";
//    }
    @PostMapping("api/users/login")
    public @ResponseBody
    ResponseEntity<CheckAnswer> checkLogin(@RequestBody LoginEmail loginEmail, HttpServletResponse res){

        User user = userService.checkLogin(loginEmail);
        //비밀번호도 저장한 후 만들어주자
        CheckAnswer ans = new CheckAnswer();

        String jwt = jwtService.create(user);
        res.setHeader("jwt-auth-token", jwt);

        if(user == null){
            ans.setCheckAnswer(200, "Success", false, 0L, "none");
            return new ResponseEntity<>(ans, HttpStatus.OK);
        }else{
            ans.setCheckAnswer(200, "Success", true, user.getIdx(), user.getGender());
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
    ResponseEntity uploadFile(@RequestParam(value = "img") MultipartFile img,
                              @RequestParam("email") String email,
                              @RequestParam(value="phone", required = false) String phone,
                              @RequestParam("nickName") @RequestBody  String nickName,
                              @RequestParam(value="age", required=false) @RequestBody String age,
                              @RequestParam(value="gender", required=false) @RequestBody String gender,
                              @RequestParam("location1") @RequestBody String location1,
                              @RequestParam("location2") @RequestBody String location2,
                              @RequestParam(value="kakao_id", required = false) @RequestBody String kakao_id,
                              @RequestParam(value= "token", required = false) @RequestBody String token )
            throws IllegalStateException, IOException {

        try {
            Long idx = userService.uploadProfile(img, email, phone, nickName, age, gender, location1, location2, kakao_id, token);
            Answer as2 = new Answer();
            as2.setAnswer(200, "Success", idx);
            return new ResponseEntity<>(as2,HttpStatus.CREATED);
        }catch(Exception e) {
            Answer as = new Answer();
            as.setAnswer(400, "Fail", 0L);
            return new ResponseEntity<>(as,HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PatchMapping("api/users/{idx}")
    public @ResponseBody
    ResponseEntity updateUser(@RequestParam(value = "img", required = false) MultipartFile img,
                              @RequestParam(value = "email", required = false) String email,
                              @RequestParam(value = "nickName", required = false) @RequestBody  String nickName,
                              @RequestParam(value="age", required=false) @RequestBody String age,
                              @RequestParam(value="gender", required=false) @RequestBody String gender,
                              @RequestParam(value="location1", required = false) @RequestBody String location1,
                              @RequestParam(value="location2", required = false) @RequestBody String location2,
                              @RequestParam(value="kakao_id", required = false) @RequestBody String kakao_id,
                              @RequestParam(value= "token", required = false) @RequestBody String token,
                              @PathVariable(value="idx") String userId ) throws IllegalStateException, IOException {

        try {
            userService.updateUser(img, email, nickName, age, gender, location1, location2, kakao_id, token, userId);
            NormalAnswer na = new NormalAnswer();
            na.setCode(200);
            na.setMsg("Success");
            return new ResponseEntity<>(na, HttpStatus.CREATED);
        }catch(Exception e){
            NormalAnswer na = new NormalAnswer();
            na.setCode(1001);
            na.setMsg("Failed");
            return new ResponseEntity<>(na, HttpStatus.EXPECTATION_FAILED);
        }
    }

}





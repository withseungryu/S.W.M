package com.example.meeting.user;


import com.example.meeting.fileupload.S3Uploader;


import com.example.meeting.jwt.JwtService;
import com.example.meeting.user.dto.Answer;
import com.example.meeting.user.dto.CheckAnswer;
import com.example.meeting.user.dto.LoginEmail;
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


    private JwtService jwtService = new JwtService();


    @PostMapping("api/users/login")
    public @ResponseBody
    ResponseEntity<CheckAnswer> checkLogin(@RequestBody LoginEmail loginEmail, HttpServletResponse res){


        User user = userRepository.findByEmail(loginEmail.getEmail());


        String token = loginEmail.getToken();
        if(user != null && token !=null) {
            if (token != user.getToken()) {
                user.setToken(token);
                userRepository.save(user);
            }
        }


        String jwt = jwtService.create(user);

        //비밀번호도 저장한 후 만들어주자
        CheckAnswer ans = new CheckAnswer();
        res.setHeader("jwt-auth-token", jwt);


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
    ResponseEntity uploadFile(@RequestParam(value = "img") MultipartFile img, @RequestParam("email") String email, @RequestParam("nickName") @RequestBody  String nickName, @RequestParam(value="age", required=false) @RequestBody String age, @RequestParam(value="gender", required=false) @RequestBody String gender,  @RequestParam("location") @RequestBody String location,  @RequestParam("kakao_id") @RequestBody String kakao_id, @RequestParam(value= "token", required = false) @RequestBody String token ) throws IllegalStateException, IOException {

        User user = new User();

        Answer as = new Answer();
        as.setAnswer(400, "Fail", 0L);

        user.setEmail(email);
        user.setImg("https://shallwemeet-bucket.s3.ap-northeast-2.amazonaws.com/static/" + "user_img_" + email+ ".jpg");
        user.setNickName(nickName);
        user.setAge(age);
        user.setLocation(location);
        user.setKakao_id(kakao_id);
//        user.setToken(token);

        if(gender != null) {
            user.setGender(gender);
        }else{
            user.setGender("homo");
        }
        if(token!=null) {
            user.setToken(token);
        }
        else{
            user.setToken("??");
        }
        user.setJwt("tmp");
        userRepository.save(user);

        String uPath = s3Uploader.upload(img, "user_img_" + email + ".jpg" );
        System.out.println(uPath);

        Long idx = userRepository.getFromEmail(email);

        Answer as2 = new Answer();
        as2.setAnswer(200, "Success", idx);

        return new ResponseEntity<>(as2,HttpStatus.CREATED);

    }





}

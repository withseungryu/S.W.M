package com.example.meeting.service;

import com.example.meeting.auth.jwt.JwtService;
import com.example.meeting.config.S3Uploader;
import com.example.meeting.dao.BoardRepository;
import com.example.meeting.dao.UserRepository;
import com.example.meeting.dto.user.LoginEmail;
import com.example.meeting.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.sql.Date;

@AllArgsConstructor
@Service
public class UserService {

    UserRepository userRepository;
    BoardRepository boardRepository;
    JwtService jwtService;
    S3Uploader s3Uploader;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cronJobSch() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        java.util.Date now = new java.util.Date();
        String strDate = sdf.format(now);
        System.out.println("Java cron job expression:: " + strDate);
        userRepository.changePoint();
        //        List<User> user = userRepository.findAll();
//        for(int i=0; i<user.size(); ++i){
//            user.get(i).setPoint(10);
//            userRepository.save(user.get(i));
//        }
        boardRepository.deleteBoard((new Date(System.currentTimeMillis())));
    }

    public User checkLogin(LoginEmail loginEmail){
        User user = userRepository.findByEmail(loginEmail.getEmail());

        String token = loginEmail.getToken();
        if(user != null && token !=null) {
            if (token != user.getToken()) {
                user.setToken(token);
                userRepository.save(user);
            }
        }
        return user;
    }

    public Long uploadProfile(MultipartFile img,
                              String email,
                              String phone,
                              String nickName,
                              String age,
                              String gender,
                              String location1,
                              String location2,
                              String kakao_id,
                              String token) throws IOException {

        User user = new User();
        user.setEmail(email);
        user.setImg("https://mimansa-bucket.s3.ap-northeast-2.amazonaws.com/static/" + "user_img_" + email+ ".jpg");
        user.setNickName(nickName);
        user.setAge(age);
        user.setPoint(10);
        user.setLocation1(location1);
        user.setLocation2(location2);
        if(phone != null) {
            user.setPhone(phone);
        }else{
            user.setPhone("01034353065");
        }
        user.setKakao_id("tmp");
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

        Long idx = userRepository.getFromEmail(email);

        return idx;
    }

    public void updateUser(MultipartFile img,
                           String email,
                           String nickName,
                           String age,
                           String gender,
                           String location1,
                           String location2,
                           String kakao_id,
                           String token,
                           String userId) throws IOException {
        User user = userRepository.findByIdx(Long.parseLong(userId));
        if(img!= null){
            String uPath = s3Uploader.upload(img, "user_img_" + user.getEmail() + ".jpg" );
        }
        if(email != null){
            user.setEmail(email);
        }
        if(nickName != null){
            user.setNickName(nickName);
        }
        if(age != null){
            user.setAge(age);
        }
        if(gender != null){
            user.setGender(gender);
        }
        if(location1 != null){
            user.setLocation1(location1);
        }
        if(location2 != null){
            user.setLocation2(location2);
        }
        if(kakao_id != null){
            user.setKakao_id(kakao_id);
        }
        userRepository.save(user);
    }
}
package com.example.meeting.dto.user;


import com.example.meeting.entity.User;
import lombok.Data;

@Data
public class UserDto {

    private Long idx;

    private String nickName;

    private String email;

    private String gender;

    private String age;

    private String location1;

    private String location2;

    private String number;
    private String kakao_id;

    private int point;

    public void setAll(User user){
        this.idx = user.getIdx();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.location1 = user.getLocation1();
        this.number = user.getPhone();
        this.kakao_id = user.getKakao_id();
        this.point = user.getPoint();
    }



}

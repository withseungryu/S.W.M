package com.example.meeting.match.dto;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
public class SenderDto {

    private Long idx;

    private String nickName;

    private String img;

    private String email;

    private String gender;

    private String age;

    private String location1;

    private String location2;

    private String kakao_id;

    private int point;

    private String token;
    private String jwt;

    private boolean status;

    private boolean is_matched;

    private Timestamp createdTime;

    public void setAll(Long user_id, String nickname, String img, String email, String gender, String age,
                       String location1, String location2, String kakao_id, int point, String token, String jwt, boolean status, boolean is_matched,
                       Timestamp createdTime){
        this.idx = user_id;
        this.nickName = nickname;
        this.img =img;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.location1 = location1;
        this.location2 = location2;
        this.kakao_id = kakao_id;
        this.point = point;
        this.token = token;
        this.jwt = jwt;
        this.status = status;
        this.is_matched = is_matched;
        this.createdTime =createdTime;


    }
}

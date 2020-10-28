package com.example.meeting.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="user")
@Table
public class User implements Serializable {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name="nickname")
    private String nickName;

    @Column
    private String img;

    @Column
    private String email;

    @Column
    private String gender;

    @Column
    private String age;

    @Column
    private String location;

    @Column
    private String kakao_id;

    @Column
    private int point;


    @Builder
    public User(String nickName, String img, String email, String gender, String age,  String location, String kakao_id, int point){
        this.nickName = nickName;
//        this.password = password;
        this.img = img;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.location = location;
        this.kakao_id = kakao_id;
        this.point = point;
    }

    public void saveUser(String email){
        this.nickName = "";
        this.img="";
        this.gender = "";
        this.age = "";
        this.email = email;

    }

}

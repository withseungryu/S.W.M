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

    @Column
    private String name;

    @Column(name="nickname")
    private String nickName;

    @Column
    private String email;

    @Column
    private String gender;

    @Column
    private String age_range;

    @Column
    private String birth;


//    @Column
//    private LocalDateTime createdDate;
//
//    @Column
//    private LocalDateTime supdatedDate;

    @Builder
    public User(String name, String email, String gender, String age_range, String birth){
        this.name =name;
//        this.password = password;
        this.email = email;
        this.gender = gender;
        this.age_range = age_range;
        this.birth = birth;
    }
}

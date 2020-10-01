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

//    @Column
//    private String password;

    @Column
    private String email;

    @Column
    private int gender;

    @Column
    private int age_range;

    @Column
    private int birth;


//    @Column
//    private LocalDateTime createdDate;
//
//    @Column
//    private LocalDateTime supdatedDate;

    @Builder
    public User(String name, String email, int gender, int age_range, int birth){
        this.name =name;
//        this.password = password;
        this.email = email;
        this.gender = gender;
        this.age_range = age_range;
        this.birth = birth;
    }
}

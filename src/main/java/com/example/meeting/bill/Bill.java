package com.example.meeting.bill;

import com.example.meeting.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Bill implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToOne
    @JoinColumn(name= "user_id")
    private User user;

    @Column
    private int point;

    @Column
    private int money;

    @Column
    private Timestamp createdDate;

    @Builder
    public Bill(User user, int point, int money, Timestamp createdDate){

        this.user =user;
        this.point = point;
        this.money = money;
        this.createdDate = createdDate;

    }
}

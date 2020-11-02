package com.example.meeting.bill;

import com.example.meeting.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Bill implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToOne
    @JoinColumn(name= "user_id")
    private User user;

    @Column
    private int point;

    @Column
    private int money;

    @Column(name="created_time")
    private Timestamp createdDate;

    @Builder
    public Bill(User user, int point, int money, Timestamp createdDate){
        this.user =user;
        this.point = point;
        this.money = money;
        this.createdDate = createdDate;

    }
    public void setCreatedDateNow(){
        LocalDateTime localDateTime = LocalDateTime.now();
        this.createdDate = Timestamp.valueOf(localDateTime);
    }
}

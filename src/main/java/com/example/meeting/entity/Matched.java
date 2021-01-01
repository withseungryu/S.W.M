package com.example.meeting.entity;

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
public class Matched implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToOne
    @JoinColumn(name= "sender")
    private User sender;

    @OneToOne
    @JoinColumn(name="list_id")
    private Board board;

    @Column
    private boolean status;

    @Column
    private boolean is_matched;

    @Column(name ="created_time")
    private Timestamp createdTime;

    @Builder
    public Matched(User sender, Board board, boolean status, boolean is_matched, Timestamp createdDate){
        this.sender = sender;
        this.board = board;
        this.status = status;
        this.is_matched = is_matched;
        this.createdTime = createdTime;
    }

    public void setCreatedDateNow(){
        LocalDateTime localDateTime = LocalDateTime.now();
        this.createdTime = Timestamp.valueOf(localDateTime);
    }

}

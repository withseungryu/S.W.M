package com.example.meeting.match;

import com.example.meeting.board.Board;
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
public class Matched implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToOne
    @JoinColumn(name= "maker")
    private User maker;

    @OneToOne
    @JoinColumn(name= "sender")
    private User sender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="list_id")
    private Board board;

    @Column
    private boolean is_matched;

    @Column(name ="created_time")
    private Timestamp createdTime;

    @Builder
    public Matched(User maker, User sender, Board board, boolean is_matched, Timestamp createdDate){
        this.maker = maker;
        this.sender = sender;
        this.board = board;
        this.is_matched = is_matched;
        this.createdTime = createdTime;
    }


}

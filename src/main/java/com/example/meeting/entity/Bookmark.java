package com.example.meeting.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Bookmark {
    @Id
    @Column(name="bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToOne
    @JoinColumn(name= "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name= "list_id")
    private Board board;

    @Builder
    public Bookmark(User user, Board board){

        this.user = user;
        this.board = board;
    }
}

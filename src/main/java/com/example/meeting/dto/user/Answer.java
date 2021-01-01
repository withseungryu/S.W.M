package com.example.meeting.dto.user;

import lombok.Data;

@Data
public class Answer {
    int code;
    String msg;
    Long idx;

    public void setAnswer(int code, String msg, Long idx){
        this.code = code;
        this.msg = msg;
        this.idx = idx;
    }
}

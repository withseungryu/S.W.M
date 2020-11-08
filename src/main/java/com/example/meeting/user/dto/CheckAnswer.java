package com.example.meeting.user.dto;

import lombok.Data;

@Data
public class CheckAnswer {
    int code;
    String msg;
    boolean is_checked;
    Long idx;

    public void setCheckAnswer(int code, String msg, boolean is_checked, Long idx){
        this.code =code;
        this.msg = msg;
        this.is_checked = is_checked;
        this.idx = idx;
    }
}

package com.example.meeting.dto.user;

import lombok.Data;

@Data
public class CheckAnswer {
    int code;
    String msg;
    boolean is_checked;
    Long idx;
    String gender;

    public void setCheckAnswer(int code, String msg, boolean is_checked, Long idx, String gender){
        this.code =code;
        this.msg = msg;
        this.is_checked = is_checked;
        this.idx = idx;
        this.gender =gender;
    }
}

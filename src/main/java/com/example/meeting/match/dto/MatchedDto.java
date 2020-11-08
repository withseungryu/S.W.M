package com.example.meeting.match.dto;

import lombok.Data;

@Data
public class MatchedDto {
    Long boardId;
    Long senderId;
    Boolean status;
}

package com.example.meeting.dto.match;

import lombok.Data;

@Data
public class MatchedDto {
    Long boardId;
    Long senderId;
    Boolean status;
}

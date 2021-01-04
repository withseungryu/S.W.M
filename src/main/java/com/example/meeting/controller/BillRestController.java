package com.example.meeting.controller;

import com.example.meeting.entity.Bill;
import com.example.meeting.dao.BillRepository;
import com.example.meeting.dto.bill.BillDto;
import com.example.meeting.dto.board.Answer;
import com.example.meeting.entity.User;
import com.example.meeting.dao.UserRepository;
import com.example.meeting.service.BillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/api/bill")
@RestController
public class BillRestController {
    private BillService billService;

    @PostMapping
    public @ResponseBody
    ResponseEntity<Answer> boardUpload(@RequestBody BillDto billDto){
        try {
            billService.boardUpload(billDto);
            Answer ans = new Answer();
            ans.setCode(200);
            ans.setMsg("Success");
            return new ResponseEntity<>(ans, HttpStatus.CREATED);
        }
        catch(Exception e){
            Answer ans = new Answer();
            ans.setCode(1001);
            ans.setMsg("Failed");
            return new ResponseEntity<>(ans, HttpStatus.EXPECTATION_FAILED);
        }
    }
}

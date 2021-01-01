package com.example.meeting.controller;

import com.example.meeting.entity.Bill;
import com.example.meeting.dao.BillRepository;
import com.example.meeting.dto.bill.BillDto;
import com.example.meeting.dto.board.Answer;
import com.example.meeting.entity.User;
import com.example.meeting.dao.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/bill")
@RestController
public class BillRestController {
    private BillRepository billRepository;
    private UserRepository userRepository;


    public BillRestController(BillRepository billRepository, UserRepository userRepository){
        this.billRepository = billRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<Answer> boardUpload(@RequestBody BillDto billDto){

        User user = userRepository.findByIdx(billDto.getUserId());

        Bill bill = new Bill();
        bill.setUser(user);
        bill.setMoney(bill.getMoney());
        bill.setPoint(bill.getMoney()/10);
        bill.setCreatedDateNow();

        billRepository.save(bill);

        Answer ans = new Answer();
        ans.setCode(200);
        ans.setMsg("Success");

        return new ResponseEntity<>(ans, HttpStatus.CREATED);

    }
}

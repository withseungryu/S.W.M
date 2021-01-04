package com.example.meeting.service;

import com.example.meeting.dao.BillRepository;
import com.example.meeting.dao.BoardRepository;
import com.example.meeting.dao.UserRepository;
import com.example.meeting.dto.bill.BillDto;
import com.example.meeting.entity.Bill;
import com.example.meeting.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BillService {
    BoardRepository boardRepository;
    UserRepository userRepository;
    BillRepository billRepository;

    public void boardUpload(BillDto billDto){
        User user = userRepository.findByIdx(billDto.getUserId());
        Bill bill = new Bill();
        bill.setUser(user);
        bill.setMoney(bill.getMoney());
        bill.setPoint(bill.getMoney()/10);
        bill.setCreatedDateNow();
        billRepository.save(bill);
    }

}

package com.example.meeting.bill;

import com.example.meeting.board.Answer;
import com.example.meeting.bookmark.BookmarkRepository;
import com.example.meeting.user.User;
import com.example.meeting.user.UserRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
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

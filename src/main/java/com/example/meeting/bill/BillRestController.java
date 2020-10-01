package com.example.meeting.bill;

import com.example.meeting.bookmark.BookmarkRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public class BillRestController {
    private BillRepository billRepository;


    public BillRestController(BillRepository billRepository){
        this.billRepository =billRepository;
    }
}

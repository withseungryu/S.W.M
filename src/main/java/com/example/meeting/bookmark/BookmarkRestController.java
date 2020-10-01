package com.example.meeting.bookmark;

import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public class BookmarkRestController {
    private BookmarkRepository bookmarkRepository;


    public BookmarkRestController(BookmarkRepository bookmarkRepository){
        this.bookmarkRepository = bookmarkRepository;
    }
}

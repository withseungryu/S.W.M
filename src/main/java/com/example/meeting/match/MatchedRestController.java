package com.example.meeting.match;

import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public class MatchedRestController {

    private MatchedRepository matchedRepository;


    public MatchedRestController(MatchedRepository matchedRepository){
        this.matchedRepository = matchedRepository;
    }
}

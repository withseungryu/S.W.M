package com.example.meeting.match;

import com.example.meeting.board.Board;
import com.example.meeting.board.BoardRepository;
import com.example.meeting.bookmark.BookmarkRepository;
import com.example.meeting.user.User;
import com.example.meeting.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/match")
public class MatchedRestController {

    private MatchedRepository matchedRepository;
    private BoardRepository boardRepository;
    private UserRepository userRepository;

    public MatchedRestController(MatchedRepository matchedRepository, UserRepository userRepository, BoardRepository boardRepository){
        this.matchedRepository = matchedRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<Matched>> getTest(){
        List<Matched> maList = matchedRepository.findAll();
        return new ResponseEntity<>(maList, HttpStatus.OK);
    }
    @GetMapping("/sender/{senderId}")
    public @ResponseBody ResponseEntity<List<Matched>> getSended(@PathVariable("senderId")Long senderId){
        User sender = userRepository.findByIdx(senderId);
        System.out.println(sender.getEmail());
        List<Matched> maList = matchedRepository.findSend(sender.getIdx());
        System.out.println(maList.size());
        return new ResponseEntity<>(maList, HttpStatus.OK);
    }


    @GetMapping("/maker/{makerId}")
    public  @ResponseBody ResponseEntity<List<Matched>> getMade(@PathVariable("makerId")Long makerId){
        User maker = userRepository.findByIdx(makerId);

        List<Matched> maList = matchedRepository.findMaker(maker.getIdx());
        return new ResponseEntity<>(maList, HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<AnswerMatch> makeMatch(@RequestBody MatchedDto matchedDto){
        Board board = boardRepository.findByIdx(matchedDto.getBoardId());
        User sender = userRepository.findByIdx(matchedDto.getSenderId());
        User maker = userRepository.findByIdx(board.getUser().getIdx());

        System.out.println(maker.getAge());

        List<Matched> matcheds = matchedRepository.findMaker(maker.getIdx());
        boolean chk = false;
        if(matcheds.size()!=0) {
            for(int i=0; i<matcheds.size(); ++i){
                if(matcheds.get(i).is_matched()){
                    chk = true;
                }
                if(chk){
                    break;
                }
            }
            if(chk){
                AnswerMatch ans = new AnswerMatch();
                ans.setAnswer(400, "Fail");
                return new ResponseEntity<>(ans, HttpStatus.OK);
            }
        }
//
        AnswerMatch ans = new AnswerMatch();

//
//
        Matched matched = new Matched();
        matched.setBoard(board);
        matched.setSender(sender);
        matched.setMaker(maker);
        matched.set_matched(false);
        matched.setCreatedDateNow();

        matchedRepository.save(matched);


        ans.setAnswer(200, "Success");

        return new ResponseEntity<>(ans, HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<AnswerMatch> updateMatch(@RequestBody MatchedDto matchedDto){
        Board board = boardRepository.findByIdx(matchedDto.getBoardId());
        User sender = userRepository.findByIdx(matchedDto.getSenderId());
        User maker = userRepository.findByIdx(board.getUser().getIdx());
        AnswerMatch ans = new AnswerMatch();
        List<Matched> matcheds = matchedRepository.findMaker(maker.getIdx());
        boolean chk = false;
        if(matcheds.size()!=0) {
            for(int i=0; i<matcheds.size(); ++i){
                if(matcheds.get(i).is_matched()){
                    chk = true;
                }
                if(chk){
                    break;
                }
            }
            if(chk){

                ans.setAnswer(400, "Fail");
                return new ResponseEntity<>(ans, HttpStatus.CREATED);

            }
        }


        Matched matched = matchedRepository.findMatched(sender.getIdx(), maker.getIdx(), board.getIdx());

        if(matched.is_matched()){
            matched.set_matched(false);
        }else {
            matched.set_matched(true);
        }
        matchedRepository.save(matched);
        if(!chk) {
            ans.setAnswer(200, "Success");
        }
        return new ResponseEntity<>(ans, HttpStatus.CREATED);
    }
}

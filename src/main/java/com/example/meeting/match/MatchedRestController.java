package com.example.meeting.match;

import com.example.meeting.board.Board;
import com.example.meeting.board.BoardRepository;
import com.example.meeting.match.dto.AnswerMatch;
import com.example.meeting.match.dto.MatchedDto;
import com.example.meeting.user.User;
import com.example.meeting.user.UserRepository;
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

        List<Matched> chkList = matchedRepository.findMatched(sender.getIdx(), board.getIdx());

        if(chkList.size() >0){
            AnswerMatch ans = new AnswerMatch();
            ans.setAnswer(401, "Fail(이미 신청한 게시판입니다.");
            return new ResponseEntity<>(ans, HttpStatus.OK);
        }

        List<Matched> matcheds = matchedRepository.findBoard(board);
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
                ans.setAnswer(400, "Fail(이미 성사된 게시판입니다.)");
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
        matched.set_matched(false);
        matched.setCreatedDateNow();

        if(matchedDto.getStatus()) {
            matched.setStatus(matchedDto.getStatus());
        }else{
            matched.setStatus(matchedDto.getStatus());
        }


        matchedRepository.save(matched);


        ans.setAnswer(200, "Success");

        return new ResponseEntity<>(ans, HttpStatus.CREATED);

    }

    @PatchMapping("/payment")
    public ResponseEntity<AnswerMatch> payMatch(@RequestBody MatchedDto matchedDto){
        Board board = boardRepository.findByIdx(matchedDto.getBoardId());
        User sender = userRepository.findByIdx(matchedDto.getSenderId());
        User maker = userRepository.findByIdx(board.getUser().getIdx());
        AnswerMatch ans = new AnswerMatch();

        List<Matched> matcheds = matchedRepository.findBoard(board);
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

                ans.setAnswer(400, "Fail(이미 성사된 매치가 있습니다)");
                return new ResponseEntity<>(ans, HttpStatus.CREATED);

            }
        }

        List<Matched> matcheds2 = matchedRepository.findMatched(sender.getIdx(), board.getIdx());


        Matched matched = matcheds2.get(0);
        if(matched.isStatus()){
            ans.setAnswer(400, "Fail(이미 결제됐는데요?)");
        }else{
            matched.setStatus(true);
            matchedRepository.save(matched);
            ans.setAnswer(200, "Success");
        }





        return new ResponseEntity<>(ans, HttpStatus.CREATED);

    }


    @PatchMapping
    public ResponseEntity<AnswerMatch> updateMatch(@RequestBody MatchedDto matchedDto){
        Board board = boardRepository.findByIdx(matchedDto.getBoardId());
        User sender = userRepository.findByIdx(matchedDto.getSenderId());

        AnswerMatch ans = new AnswerMatch();
        List<Matched> matcheds = matchedRepository.findBoard(board);
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

                ans.setAnswer(400, "Fail(이미 성사된 매치가 있습니다)");
                return new ResponseEntity<>(ans, HttpStatus.CREATED);

            }
        }


        List<Matched> matched2 = matchedRepository.findMatched(sender.getIdx(), board.getIdx());

        System.out.println(matched2.size());
        Matched matched = matched2.get(0);

        if(matched.isStatus()) {

            if (matched.is_matched()) {
                matched.set_matched(false);
            } else {
                matched.set_matched(true);
            }
            matchedRepository.save(matched);
            if (!chk) {
                ans.setAnswer(200, "Success(성사 완료)");
            }
            return new ResponseEntity<>(ans, HttpStatus.CREATED);
        }else{
            ans.setAnswer(401, "Fail(상대가 본인에게 결제를 신청했습니다.)");
            return new ResponseEntity<>(ans, HttpStatus.CREATED);
        }
    }
}

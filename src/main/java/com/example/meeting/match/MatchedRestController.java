package com.example.meeting.match;

import com.example.meeting.board.Board;
import com.example.meeting.board.BoardRepository;
import com.example.meeting.board.dto.BoardDto;
import com.example.meeting.bookmark.Bookmark;
import com.example.meeting.match.dto.*;
import com.example.meeting.user.User;
import com.example.meeting.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("matched/{makerId}")
    public @ResponseBody ResponseEntity<MatchedAllDto> getMatchedList(@PathVariable("makerId")Long makerId){
        List<Matched> maList = matchedRepository.findMatch(makerId);
        List<Matched> senders = new ArrayList<>();
        List<Matched> makers = new ArrayList<>();
        MatchedAllDto matchedAllDto = new MatchedAllDto();
        for(int i=0; i<maList.size(); ++i){
            if(maList.get(i).getSender().getIdx() == makerId){
                senders.add(maList.get(i));
            }
            else{
                makers.add(maList.get(i));
            }

        }
        matchedAllDto.setIs_senders(senders);
        matchedAllDto.setIs_makers(makers);
        return new ResponseEntity<>(matchedAllDto, HttpStatus.OK);
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
    public  @ResponseBody ResponseEntity<List<MakerBoardDto>> getMade(@PathVariable("makerId")Long makerId){
        User maker = userRepository.findByIdx(makerId);

        List<MakerBoardDto> makerDtos = new ArrayList<>();

        List<Object[]> boards = boardRepository.findUser(maker);

        List<SenderDto> senderDtos = new ArrayList<>();


        System.out.println(boards.size());
        for(int i=0; i<boards.size()-1; ++i) {

            BoardDto board = new BoardDto();
            Board b = (Board) boards.get(i)[0];
            Matched m = (Matched) boards.get(i)[1];
            Board b2 = (Board) boards.get(i+1)[0];
            Matched m2 = (Matched) boards.get(i+1)[1];
            Long idx = b.getIdx();

            if(m == null){
                MakerBoardDto mt = new MakerBoardDto();
                List<SenderDto> new_senderDtos = new ArrayList<>();
                mt.setAll(idx,b.getTitle(),b.getImg1(), b.getImg2(), b.getImg3(), b.getTag1(), b.getTag2(), b.getTag3(), b.getLocation1(), b.getLocation2(), b.getNum_type(), b.getAge(), b.getGender(), b.getDate(), b.getDate2(), b.getCreatedDate(),b.getUpdatedDate(),  new_senderDtos );
                makerDtos.add(mt);
                new_senderDtos.clear();
                senderDtos.clear();


                if(i== boards.size()-2){
                    MakerBoardDto mt2 = new MakerBoardDto();
                    List<SenderDto> new_senderDtos2 = new ArrayList<>();
                    if(m2 != null) {
                        SenderDto senderDto2 = new SenderDto();


                        senderDto2.setAll(m2.getSender().getIdx(), m2.getSender().getNickName(),
                                m2.getSender().getImg(), m2.getSender().getEmail(),
                                m2.getSender().getGender(), m2.getSender().getAge(), m2.getSender().getLocation(),
                                m2.getSender().getKakao_id(), m2.getSender().getPoint(), m2.getSender().getToken(), m2.getSender().getJwt(), m2.isStatus(),
                                m2.is_matched(), m2.getCreatedTime());
                        senderDtos.add(senderDto2);


                        new_senderDtos2.addAll(senderDtos);

                    }
                    mt2.setAll(b2.getIdx(), b2.getTitle(),  b2.getImg1(),  b2.getImg2(),  b2.getImg3(), b2.getTag1(), b2.getTag2(), b2.getTag3(), b2.getLocation1(), b2.getLocation2(), b2.getNum_type(), b2.getAge(), b2.getGender(), b2.getDate(),  b2.getDate2(), b2.getCreatedDate(), b2.getUpdatedDate(), new_senderDtos );
                    new_senderDtos.clear();
                    makerDtos.add(mt2);
                    senderDtos.clear();
                }


                continue;
            }




            SenderDto senderDto = new SenderDto();
            senderDto.setAll(m.getSender().getIdx(), m.getSender().getNickName(),
                    m.getSender().getImg(), m.getSender().getEmail(),
                    m.getSender().getGender(), m.getSender().getAge(), m.getSender().getLocation(),
                    m.getSender().getKakao_id(), m.getSender().getPoint(),  m.getSender().getToken(), m.getSender().getJwt(),m.isStatus(),
                    m.is_matched(), m.getCreatedTime());

            senderDtos.add(senderDto);


            if(idx != b2.getIdx()){
                MakerBoardDto mt = new MakerBoardDto();
                List<SenderDto> new_senderDtos = new ArrayList<>();
                new_senderDtos.addAll(senderDtos);
                mt.setAll(idx,m.getBoard().getTitle(), m.getBoard().getImg1(), m.getBoard().getImg2(),  m.getBoard().getImg3(), m.getBoard().getTag1(), m.getBoard().getTag2(), m.getBoard().getTag3(), m.getBoard().getLocation1(), m.getBoard().getLocation2(), m.getBoard().getNum_type(), m.getBoard().getAge(), m.getBoard().getGender(), m.getBoard().getDate(),  m.getBoard().getDate2(), m.getBoard().getCreatedDate(), m.getBoard().getUpdatedDate(),  new_senderDtos );
                makerDtos.add(mt);
                new_senderDtos.clear();
                senderDtos.clear();

                if(boards.size()-2 == i){
                    List<SenderDto> new_senderDtos2 = new ArrayList<>();
                    if(m2 != null) {

                        SenderDto senderDto2 = new SenderDto();
                        senderDto2.setAll(m2.getSender().getIdx(), m2.getSender().getNickName(),
                                m2.getSender().getImg(), m2.getSender().getEmail(),
                                m2.getSender().getGender(), m2.getSender().getAge(), m2.getSender().getLocation(),
                                m2.getSender().getKakao_id(), m2.getSender().getPoint(),  m2.getSender().getToken(), m2.getSender().getJwt(),m2.isStatus(),
                                m2.is_matched(), m2.getCreatedTime());
                        senderDtos.add(senderDto2);



                        new_senderDtos2.addAll(senderDtos);
                    }
                    MakerBoardDto mt2 = new MakerBoardDto();
                    mt2.setAll(b2.getIdx(), b2.getTitle(),  b2.getImg1(),  b2.getImg2(),  b2.getImg3(), b2.getTag1(), b2.getTag2(), b2.getTag3(), b2.getLocation1(), b2.getLocation2(), b2.getNum_type(), b2.getAge(), b2.getGender(),b2.getDate(), b2.getDate2(),  b2.getCreatedDate(), b2.getUpdatedDate(), new_senderDtos2 );

                    makerDtos.add(mt2);
                    new_senderDtos2.clear();
                    senderDtos.clear();
                }

            }else{
                if(i== boards.size()-2){

                    SenderDto senderDto2 = new SenderDto();
                    senderDto2.setAll(m2.getSender().getIdx(), m2.getSender().getNickName(),
                            m2.getSender().getImg(), m2.getSender().getEmail(),
                            m2.getSender().getGender(), m2.getSender().getAge(), m2.getSender().getLocation(),
                            m2.getSender().getKakao_id(), m2.getSender().getPoint(),  m2.getSender().getToken(), m2.getSender().getJwt(), m2.isStatus(),
                            m2.is_matched(), m2.getCreatedTime());
                    senderDtos.add(senderDto2);


                    MakerBoardDto mt = new MakerBoardDto();
                    List<SenderDto> new_senderDtos = new ArrayList<>();
                    new_senderDtos.addAll(senderDtos);
                    mt.setAll(idx, m.getBoard().getTitle(),  m.getBoard().getImg1(),  m.getBoard().getImg2(),  m.getBoard().getImg3(), m.getBoard().getTag1(), m.getBoard().getTag2(), m.getBoard().getTag3(), m.getBoard().getLocation1(), m.getBoard().getLocation2(), m.getBoard().getNum_type(), m.getBoard().getAge(), m.getBoard().getGender(), m.getBoard().getDate(), m.getBoard().getDate2(), m.getBoard().getCreatedDate(), m.getBoard().getUpdatedDate(), new_senderDtos );
                    new_senderDtos.clear();
                    makerDtos.add(mt);
                    senderDtos.clear();
                }
            }


        }

        for(int i=0; i<makerDtos.size(); ++i){
            for(int j=0; j<makerDtos.get(i).getSenders().size(); ++j){
                if(makerDtos.get(i).getSenders().get(j).is_matched()){
                    makerDtos.remove(i);
                    i--;
                    break;
                }
            }
        }

        return new ResponseEntity<>(makerDtos, HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<AnswerMatch> makeMatch(@RequestBody MatchedDto matchedDto){
        Board board = boardRepository.findByIdx(matchedDto.getBoardId());
        User sender = userRepository.findByIdx(matchedDto.getSenderId());
        User maker = userRepository.findByIdx(board.getUser().getIdx());

        if(sender.equals(maker)){
            AnswerMatch ans = new AnswerMatch();
            ans.setAnswer(403, "당신이 만든 게시판입니다.");
            return new ResponseEntity<>(ans, HttpStatus.OK);
        }


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

            if(sender.getPoint() >=50) {
                sender.setPoint(sender.getPoint() - 50);
                userRepository.save(sender);
            }else{
                ans.setAnswer(402, "포인트가 부족합니다.....");
                return new ResponseEntity<>(ans, HttpStatus.CREATED);
            }
            matched.setStatus(matchedDto.getStatus());
        }else{
            matched.setStatus(matchedDto.getStatus());
        }

        ans.setAnswer(200, "Success");

        matchedRepository.save(matched);



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

                ans.setAnswer(401, "Fail(이미 성사된 매치가 있습니다)");
                return new ResponseEntity<>(ans, HttpStatus.CREATED);

            }
        }

        List<Matched> matcheds2 = matchedRepository.findMatched(sender.getIdx(), board.getIdx());


        Matched matched = matcheds2.get(0);
        if(matched.isStatus()){
            ans.setAnswer(400, "Fail(이미 결제됐는데요?)");
        }else{
            if(maker.getPoint() >=50) {
                maker.setPoint(maker.getPoint() - 50);
                userRepository.save(maker);
            }else{
                ans.setAnswer(402, "포인트가 부족합니다.....");
                return new ResponseEntity<>(ans, HttpStatus.CREATED);
            }
            matched.setStatus(true);
            matched.set_matched(true);
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

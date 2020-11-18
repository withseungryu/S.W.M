package com.example.meeting.board;

import com.example.meeting.board.dto.Answer;
import com.example.meeting.board.dto.BoardDto;
import com.example.meeting.bookmark.Bookmark;
import com.example.meeting.fileupload.S3Uploader;
import com.example.meeting.user.User;
import com.example.meeting.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/boards")
public class BoardRestController {

    private BoardRepository boardRepository;
    private UserRepository userRepository;
    private S3Uploader s3Uploader;

    @GetMapping
    public ResponseEntity<List<BoardDto>> getBoard(Pageable pageable,
                                                   @RequestParam(value = "location1", required = false) String location1,
                                                   @RequestParam(value = "location2", required = false) String location2,
                                                   @RequestParam(value ="num_type" ,required = false) String num_type,
                                                   @RequestParam(value ="age", required = false) String age,
                                                   @RequestParam(value = "userId", required = false) Long userId,
                                                   @RequestParam(value="gender", required = false) String gender
    ) throws IOException, ParseException {

        Page<Object[]> boards;
        User user = userRepository.findByIdx(userId);


        String bdate1 =  "2000-01-01 00:00:00";
        String bdate2 = "2100-01-01 00:00:00";


        java.sql.Timestamp date1 = Timestamp.valueOf(bdate1);
        java.sql.Timestamp date2 = Timestamp.valueOf(bdate2);


        System.out.println(date1);
        System.out.println(date2);
        if(location1 == null && location2 == null && num_type == null && age == null &&  (gender == null || gender.equals("상관없음"))){
            boards = boardRepository.getfindNullAll(user, date1, date2, pageable);
        }

        else if(location1.equals("상관없음") && location2.equals("상관없음") && num_type.equals("상관없음") && age.equals("상관없음")){

            boards = boardRepository.getfindAll(user, date1, date2, gender, pageable);
        }else if(location1.equals("상관없음") && location2.equals("상관없음") && num_type.equals("상관없음")){
            int age1 = Integer.parseInt(age) +3;
            int age2 = Integer.parseInt(age) -3;
            boards = boardRepository.getList3(age1, age2, user, date1, date2,  gender, pageable);
        }else if(location1.equals("상관없음") && location2.equals("상관없음") && age.equals("상관없음")){
            System.out.println("3");
            boards = boardRepository.getList2(num_type, user,  date1, date2, gender, pageable);
        }
        else if(age.equals("상관없음") && num_type.equals("상관없음")){
            if(location2.equals("상관없음")){
                boards = boardRepository.getList8(location1, user,   date1, date2, gender,pageable);
            }else{
                boards = boardRepository.getList1(location1, location2, user, date1, date2,  gender, pageable);
            }

        }else if(location1.equals("상관없음") && location2.equals("상관없음")){
            int age1 = Integer.parseInt(age) +3;
            int age2 = Integer.parseInt(age) -3;
            boards = boardRepository.getList6(num_type, age1, age2, user,  date1, date2, gender, pageable);
        }else if(num_type.equals("상관없음")){
            int age1 = Integer.parseInt(age) +3;
            int age2 = Integer.parseInt(age) -3;
            if(location2.equals("상관없음")) {
                boards = boardRepository.getList9(location1, age1, age2, user, date1, date2, gender, pageable);
            }else{
                boards = boardRepository.getList5(location1, location2, age1,age2, user, date1, date2, gender, pageable);
            }


        }else if(age.equals("상관없음")){
            if(location2.equals("상관없음")){
                boards = boardRepository.getList10(location1, num_type, user, date1, date2, gender, pageable);
            }else{
                boards = boardRepository.getList4(location1, location2, num_type, user,  date1, date2,gender, pageable);
            }

        }else{
            int age1 = Integer.parseInt(age) +3;
            int age2 = Integer.parseInt(age) -3;
            if(location2.equals("상관없음")){
                boards = boardRepository.getList11(location1, num_type, age1, age2, user, date1, date2, gender, pageable);
            }else{
                boards = boardRepository.getList7(location1, location2, num_type, age1, age2, user,  date1, date2, gender,pageable);
            }

        }


        if(boards.getContent().size()==0){
            List<BoardDto> adaBoard = new ArrayList<>();
            return new ResponseEntity<>(adaBoard, HttpStatus.OK);
        }
        List<BoardDto> adaBoard = adaBookmark(boards.getContent(), userId);


        return new ResponseEntity<>(adaBoard, HttpStatus.OK);


    }

    public List<BoardDto> adaBookmark(List<Object[]> tmp, Long userId){

        List<BoardDto> boards = new ArrayList<>();
        BoardDto be_board = new BoardDto();
        Long saveIdx = 0L;
        boolean ok = false;
        boolean chk = false;
        boolean last_chk = false;
        int ch_idx = 0;
        for(Object[] o : tmp) {

            BoardDto board = new BoardDto();
            Board b = (Board) o[0];
            Bookmark m = (Bookmark) o[1];

            if(m==null){
                board.setBookAll(b.getIdx(), b.getTitle(), b.getImg1(), b.getImg2(), b.getImg3(),
                        b.getTag1(), b.getTag2(), b.getTag3(), b.getLocation1(), b.getLocation2(), b.getNum_type(), b.getAge(),  b.getGender(),
                       b.getCreatedDate(), b.getUpdatedDate(), b.getUser(), false
                );

            }else{
                board.setBookAll(b.getIdx(), b.getTitle(), b.getImg1(), b.getImg2(), b.getImg3(),
                        b.getTag1(), b.getTag2(), b.getTag3(), b.getLocation1(), b.getLocation2(), b.getNum_type(), b.getAge(), b.getGender(),
                      b.getCreatedDate(), b.getUpdatedDate(), b.getUser(), true
                );
            }
            BoardDto tmpBoard = new BoardDto();
            tmpBoard.cloneBoard(board);
            boards.add(tmpBoard);

        }
        return boards;
    }


    @PostMapping
    public @ResponseBody ResponseEntity<Answer> boardUpload(@RequestParam(value = "img1") MultipartFile img1,
                                                            @RequestParam(value="img2") MultipartFile img2,
                                                            @RequestParam(value = "img3") MultipartFile img3,
                                                            @RequestParam("title") @RequestBody String ptitle,
                                                            @RequestParam("location1") @RequestBody String plocation1,
                                                            @RequestParam("location2") @RequestBody String plocation2,
                                                            @RequestParam("num_type") @RequestBody String pnum_type,
                                                            @RequestParam("gender") @RequestBody String pgender,
                                                            @RequestParam(value="tag1", required = false) @RequestBody String ptag1,
                                                            @RequestParam(value="tag2", required = false) @RequestBody String ptag2,
                                                            @RequestParam(value="tag3", required = false) @RequestBody String ptag3,
                                                            @RequestParam("average_age") @RequestBody String page,
                                                            @RequestParam(value = "user", required = false) @RequestBody String puser
    ) throws IOException, ParseException {




        String title = ptitle;
        String tag1 = ptag1;
        String tag2 = ptag2;
        String tag3 = ptag3;
        String location1 = plocation1;
        String location2 = plocation2;
        String num_type = pnum_type;
        String age = page;
//        String gender = pgender;
        if(pgender.equals("male")){
            pgender = "male";
        }else{
            pgender = "female";
        }

        ///게시판 추가 부분
        Integer latest_data = boardRepository.test();

        if(latest_data == null){
            latest_data = 0;
        }

        Board board = new Board();
        board.setTitle(title);
        board.setImg1("https://shallwemeet-bucket.s3.ap-northeast-2.amazonaws.com/static/" + "board_img_" + Integer.toString(latest_data+1) + "_1.jpg");
        board.setImg2("https://shallwemeet-bucket.s3.ap-northeast-2.amazonaws.com/static/" + "board_img_" + Integer.toString(latest_data+1) + "_2.jpg");
        board.setImg3("https://shallwemeet-bucket.s3.ap-northeast-2.amazonaws.com/static/" + "board_img_" + Integer.toString(latest_data+1) + "_3.jpg");
        board.setTag1(tag1);
        board.setTag2(tag2);
        board.setTag3(tag3);
        board.setLocation1(location1);
        board.setLocation2(location2);
        board.setNum_type(num_type);
        board.setAge(Integer.parseInt(age));
        board.setGender(pgender);
        board.setUser(userRepository.findByIdx(Long.parseLong(puser)));
        board.setCreatedDateNow();
        board.setUpdatedDateNow();

        boardRepository.save(board);





        /// 이미지 추가 부분

        int latest_id = boardRepository.test();
        System.out.println(latest_id);
        String rPath1 = s3Uploader.upload(img1, "board_img_" + Integer.toString(latest_id) + "_1.jpg" );
        String rPath2 = s3Uploader.upload(img2, "board_img_" + Integer.toString(latest_id) + "_2.jpg");
        String rPath3 = s3Uploader.upload(img3, "board_img_" + Integer.toString(latest_id) + "_3.jpg");
        System.out.println(rPath1);
        System.out.println(rPath2);
        System.out.println(rPath3);

        Answer ans = new Answer();
        ans.setAnswer(200, "Success");

        return new ResponseEntity<>(ans, HttpStatus.CREATED);


    }

    @GetMapping("/{boardId}/{userId}")
    public ResponseEntity<BoardDto> putBoard(@PathVariable("boardId")Long boardId, @PathVariable("userId") Long userId){
        Board board = boardRepository.findByIdx(boardId);
        User user = userRepository.findByIdx(userId);

        List<Object[]> object = boardRepository.noBoard(user, boardId);

        Board b = (Board) object.get(0)[0];
        Bookmark m = (Bookmark) object.get(0)[1];

        BoardDto boardDto = new BoardDto();

        if(m==null){
            boardDto.setBookAll(b.getIdx(), b.getTitle(), b.getImg1(), b.getImg2(), b.getImg3(),
                    b.getTag1(), b.getTag2(), b.getTag3(), b.getLocation1(), b.getLocation2(), b.getNum_type(), b.getAge(), b.getGender(),
                     b.getCreatedDate(), b.getUpdatedDate(), b.getUser(), false
            );
        }else{
            boardDto.setBookAll(b.getIdx(), b.getTitle(), b.getImg1(), b.getImg2(), b.getImg3(),
                    b.getTag1(), b.getTag2(), b.getTag3(), b.getLocation1(), b.getLocation2(), b.getNum_type(), b.getAge(), b.getGender(),
                    b.getCreatedDate(), b.getUpdatedDate(), b.getUser(), true
            );
        }


        return new ResponseEntity<>(boardDto, HttpStatus.OK);
    }
    @PutMapping("/{idx}")
    public ResponseEntity<?> putBoard(@PathVariable("idx")Long idx, @RequestBody Board board){
        Board persistBoard = boardRepository.getOne(idx);
        persistBoard.update(board);
        boardRepository.save(persistBoard);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<Answer> deleteBoard(@PathVariable("idx")Long idx){
        boardRepository.deleteById(idx);
        Answer ans = new Answer();
        ans.setAnswer(200, "success");
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    @GetMapping("/rec/{idx}")
    public ResponseEntity<List<Board>> recBoard(@PathVariable("idx") Long idx){
        Pageable pageable = PageRequest.of(0, 5);
        Page<Board> recs = boardRepository.rec1(pageable);

        if(idx == 1L){
            recs = boardRepository.rec1(pageable);


        }else{

        }
        List<Board> boards = recs.getContent();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }


}

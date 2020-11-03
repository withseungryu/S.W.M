package com.example.meeting.board;

import com.example.meeting.bookmark.Bookmark;
import com.example.meeting.fileupload.S3Uploader;
import com.example.meeting.user.User;
import com.example.meeting.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
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
                                                   @RequestParam(value = "location", required = false) String location,
                                                   @RequestParam(value ="num_type" ,required = false) String num_type,
                                                   @RequestParam(value ="age", required = false) String age,
                                                   @RequestParam(value = "userId", required = false) Long userId
    ) throws IOException{

        Page<Object[]> boards;
        User user = userRepository.findByIdx(userId);

        if(location == null && num_type == null && age == null){
            System.out.println("fd");
            boards = boardRepository.getfindAll(user, pageable);
        }

        else if(location.equals("상관없음") && num_type.equals("상관없음") && age.equals("상관없음")){
            System.out.println("1");
            boards = boardRepository.getfindAll(user, pageable);
        }else if(location.equals("상관없음") && num_type.equals("상관없음")){
            System.out.println("2");
            int age1 = Integer.parseInt(age) +3;
            int age2 = Integer.parseInt(age) -3;
            boards = boardRepository.getList3(age1, age2, user, pageable);
        }else if(location.equals("상관없음") && age.equals("상관없음")){
            System.out.println("3");
            boards = boardRepository.getList2(num_type, user, pageable);
        }else if(age.equals("상관없음") && num_type.equals("상관없음")){
            System.out.println("4");
            boards = boardRepository.getList1(location, user, pageable);
        }else if(location.equals("상관없음")){
            System.out.println("5");
            int age1 = Integer.parseInt(age) +3;
            int age2 = Integer.parseInt(age) -3;
            boards = boardRepository.getList6(num_type, age1, age2, user, pageable);
        }else if(num_type.equals("상관없음")){
            System.out.println("6");
            int age1 = Integer.parseInt(age) +3;
            int age2 = Integer.parseInt(age) -3;
            boards = boardRepository.getList5(location, age1,age2, user, pageable);

        }else if(age.equals("상관없음")){
            System.out.println("7");
            boards = boardRepository.getList4(location, num_type, user, pageable);
        }else{
            int age1 = Integer.parseInt(age) +3;
            int age2 = Integer.parseInt(age) -3;
            boards = boardRepository.getList7(location, num_type, age1, age2, user, pageable);
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
                        b.getTag1(), b.getTag2(), b.getTag3(), b.getLocation(), b.getNum_type(), b.getAge(), b.getGender(),
                        b.getCreatedDate(), b.getUpdatedDate(), b.getUser(), false
                );

            }else{
                board.setBookAll(b.getIdx(), b.getTitle(), b.getImg1(), b.getImg2(), b.getImg3(),
                        b.getTag1(), b.getTag2(), b.getTag3(), b.getLocation(), b.getNum_type(), b.getAge(), b.getGender(),
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
                                                            @RequestParam("location") @RequestBody String plocation,
                                                            @RequestParam("num_type") @RequestBody String pnum_type,
                                                            @RequestParam("gender") @RequestBody String pgender,
                                                            @RequestParam(value="tag1", required = false) @RequestBody String ptag1,
                                                            @RequestParam(value="tag2", required = false) @RequestBody String ptag2,
                                                            @RequestParam(value="tag3", required = false) @RequestBody String ptag3,
                                                            @RequestParam("average_age") @RequestBody String page,
                                                            @RequestParam(value = "user", required = false) @RequestBody String puser
    )  throws IOException {




        String title = ptitle;
        String tag1 = ptag1;
        String tag2 = ptag2;
        String tag3 = ptag3;
        String location = plocation;
        String num_type = pnum_type;

        String age = page;  //안보내줘도 될듯
        String gender = pgender; //안보내줘도 될듯

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
        board.setLocation(location);
        board.setNum_type(num_type);
        board.setAge(Integer.parseInt(age));
        board.setGender(gender);
        board.setUser(userRepository.getOne(1L));
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

    @GetMapping("/{idx}")
    public ResponseEntity<Board> putBoard(@PathVariable("idx")Long idx){
        Board board = boardRepository.findByIdx(idx);

        return new ResponseEntity<>(board, HttpStatus.OK);
    }
    @PutMapping("/{idx}")
    public ResponseEntity<?> putBoard(@PathVariable("idx")Long idx, @RequestBody Board board){
        Board persistBoard = boardRepository.getOne(idx);
        persistBoard.update(board);
        boardRepository.save(persistBoard);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteBoard(@PathVariable("idx")Long idx){
        boardRepository.deleteById(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }


}

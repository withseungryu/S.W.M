package com.example.meeting.service;

import com.example.meeting.config.S3Uploader;
import com.example.meeting.dao.BoardRepository;
import com.example.meeting.dao.UserRepository;
import com.example.meeting.dto.board.Answer;
import com.example.meeting.dto.board.BoardDto;
import com.example.meeting.entity.Board;
import com.example.meeting.entity.Bookmark;
import com.example.meeting.entity.Matched;
import com.example.meeting.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static com.example.meeting.service.BoardService.randomAlphaWord;

@AllArgsConstructor
@Service
public class BoardService {

    BoardRepository boardRepository;
    UserRepository userRepository;
    S3Uploader s3Uploader;

    public List<BoardDto> getBoards(Pageable pageable,
                                    String location1,
                                    String location2,
                                    String num_type,
                                    String age,
                                    Long userId,
                                    String gender,
                                    String pdate){
        Page<Object[]> boards;
        User user = userRepository.findByIdx(userId);
////Created Date
//        String bdate1 =  "2000-01-01 00:00:00";
//        String bdate2 = "2100-01-01 00:00:00";
//
//
//        java.sql.Timestamp date1 = Timestamp.valueOf(bdate1);
//        java.sql.Timestamp date2 = Timestamp.valueOf(bdate2);
//
//
//        System.out.println(date1);
//        System.out.println(date2);

        String bpdate1;
        String bpdate2;
        //Date
        if(pdate == null || pdate.equals("상관없음")){
            bpdate1 = "2000-01-01 00:00:00";
            bpdate2 = "2100-01-01 00:00:00";
        }else {
            bpdate1 = pdate + " 00:00:00";
            bpdate2 = pdate + " 23:59:59";
        }
        java.sql.Timestamp date1 = Timestamp.valueOf(bpdate1);
        java.sql.Timestamp date2 = Timestamp.valueOf(bpdate2);

        if(gender.equals("male")){
            gender = "female";
        }else if(gender.equals("female")){
            gender = "male";
        }
        if(location1 == null && location2 == null && num_type == null && age == null &&  (gender == null || gender.equals("상관없음"))){
            boards = boardRepository.getfindNullAll(user, date1, date2, pageable);
        }

        else if(location1.equals("상관없음") && location2.equals("상관없음") && num_type.equals("상관없음") && age.equals("상관없음")){
            System.out.println("ddd");
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
            return adaBoard;
        }
        List<BoardDto> adaBoard = adaBookmark(boards.getContent(), userId);
        return adaBoard;
    }

    public List<BoardDto> adaBookmark(List<Object[]> tmp, Long userId){

        System.out.println(tmp.size());
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

            if (m == null) {
                board.setBookAll(b.getIdx(), b.getTitle(), b.getImg1(), b.getImg2(), b.getImg3(),
                        b.getTag1(), b.getTag2(), b.getTag3(), b.getLocation1(), b.getLocation2(), b.getNum_type(), b.getAge(), b.getGender(),
                        b.getDate(), b.getCreatedDate(), b.getUpdatedDate(), b.getUser(), false
                );

            } else {
                board.setBookAll(b.getIdx(), b.getTitle(), b.getImg1(), b.getImg2(), b.getImg3(),
                        b.getTag1(), b.getTag2(), b.getTag3(), b.getLocation1(), b.getLocation2(), b.getNum_type(), b.getAge(), b.getGender(),
                        b.getDate(), b.getCreatedDate(), b.getUpdatedDate(), b.getUser(), true
                );
            }

            BoardDto tmpBoard = new BoardDto();
            tmpBoard.cloneBoard(board);
            boards.add(tmpBoard);
        }
        return boards;
    }

    public void uploadBoard( MultipartFile img1,
                               MultipartFile img2,
                               MultipartFile img3,
                               String ptitle,
                               String plocation1,
                               String plocation2,
                               String pnum_type,
                               String pgender,
                               String ptag1,
                               String ptag2,
                               String ptag3,
                               String page,
                               String pdate,
                               String puser) throws ParseException, IOException {
        String title = ptitle;
        String tag1 = ptag1;
        String tag2 = ptag2;
        String tag3 = ptag3;
        String location1 = plocation1;
        String location2 = plocation2;
        String num_type = pnum_type;
        String age = page;

        if(pgender.equals("male")){
            pgender = "male";
        }else{
            pgender = "female";
        }
        if(pdate==null){
            pdate = "2020-12-10";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = format.parse(pdate);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());


        Board board = new Board();
        board.setTitle(title);
        String imgName = randomAlphaWord(25);
        Board board_img = boardRepository.searchImg(imgName);
        if(board_img != null){
            imgName = randomAlphaWord(25);
        }

        board.setImg1("https://mimansa-bucket.s3.ap-northeast-2.amazonaws.com/static/" + imgName + "_1.jpg");
        board.setImg2("https://mimansa-bucket.s3.ap-northeast-2.amazonaws.com/static/" + imgName + "_2.jpg");
        board.setImg3("https://mimansa-bucket.s3.ap-northeast-2.amazonaws.com/static/" + imgName + "_3.jpg");
        board.setTag1(tag1);
        board.setTag2(tag2);
        board.setTag3(tag3);
        board.setLocation1(location1);
        board.setLocation2(location2);
        board.setNum_type(num_type);
        board.setAge(Integer.parseInt(age));
        board.setGender(pgender);
        board.setDate(sqlDate);
        board.setUser(userRepository.findByIdx(Long.parseLong(puser)));
        board.setCreatedDateNow();
        board.setUpdatedDateNow();
        boardRepository.save(board);

        s3Uploader.upload(img1, imgName + "_1.jpg" );
        s3Uploader.upload(img2, imgName + "_2.jpg");
        s3Uploader.upload(img3, imgName + "_3.jpg");
    }

    public BoardDto putBoard(Long boardId, Long userId){
        Board board = boardRepository.findByIdx(boardId);
        User user = userRepository.findByIdx(userId);

        List<Object[]> object = boardRepository.noBoard(user, boardId);

        Board b = (Board) object.get(0)[0];
        Bookmark m = (Bookmark) object.get(0)[1];

        BoardDto boardDto = new BoardDto();

        if(m==null){
            boardDto.setBookAll(b.getIdx(), b.getTitle(), b.getImg1(), b.getImg2(), b.getImg3(),
                    b.getTag1(), b.getTag2(), b.getTag3(), b.getLocation1(), b.getLocation2(), b.getNum_type(), b.getAge(), b.getGender(),
                    b.getDate(), b.getCreatedDate(), b.getUpdatedDate(), b.getUser(), false
            );
        }else{
            boardDto.setBookAll(b.getIdx(), b.getTitle(), b.getImg1(), b.getImg2(), b.getImg3(),
                    b.getTag1(), b.getTag2(), b.getTag3(), b.getLocation1(), b.getLocation2(), b.getNum_type(), b.getAge(), b.getGender(),
                    b.getDate(), b.getCreatedDate(), b.getUpdatedDate(), b.getUser(), true
            );
        }
        return boardDto;
    }

    public List<Board> recTimeBoard(Long idx){
        User user = userRepository.findByIdx(idx);

        String gender = user.getGender();
        if(gender.equals("male")){
            gender = "female";
        }
        else{
            gender = "male";
        }
        List<Board> recs = boardRepository.rec1(gender);

        List<Board> boards = new ArrayList<>();
        int flag = 0;
        for(int i=0; i<recs.size(); ++i){
            Matched m = boardRepository.recFor(recs.get(i).getIdx());

            if(flag==5){
                break;
            }

            if(m==null){

                boards.add(recs.get(i));
                flag++;
            }
        }
        return boards;
    }

    public List<Board> recLocationBoard(Long idx){
        User user = userRepository.findByIdx(idx);

        String gender =user.getGender();
        if(gender.equals("male")){
            gender = "female";
        }
        else{
            gender = "male";
        }
        List<Board> recs = boardRepository.rec2(user.getLocation1(), user.getLocation2(), gender);
        List<Board> boards = new ArrayList<>();
        int flag = 0;
        for(int i=0; i<recs.size(); ++i){
            Matched m = boardRepository.recFor(recs.get(i).getIdx());

            if(flag==5){
                break;
            }

            if(m==null){
                boards.add(recs.get(i));
                flag++;
            }
        }
        return boards;
    }

    public static String randomAlphaWord(int wordLength) {

        Random r = new Random();
        StringBuilder sb = new StringBuilder(wordLength);
        for(int i = 0; i < wordLength; i++) {
            char tmp = (char) ('a' + r.nextInt('z' - 'a'));
            sb.append(tmp);
        }
        return sb.toString();
    }

    public void putBoard(Long idx, Board board){
        Board persistBoard = boardRepository.getOne(idx);
        persistBoard.update(board);
        boardRepository.save(persistBoard);
    }

    public void deleteBoard(Long idx){
        boardRepository.deleteById(idx);
    }
}

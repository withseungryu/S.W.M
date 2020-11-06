package com.example.meeting.bookmark;

import com.example.meeting.board.Board;
import com.example.meeting.board.BoardRepository;
import com.example.meeting.user.User;
import com.example.meeting.user.UserRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class BookmarkRestController {
    private BookmarkRepository bookmarkRepository;
    private UserRepository userRepository;
    private BoardRepository boardRepository;


    public BookmarkRestController(BookmarkRepository bookmarkRepository, UserRepository userRepository, BoardRepository boardRepository){
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }


    @GetMapping("api/bookmark/{userId}")
    public ResponseEntity<List<Bookmark>> getBookmark(@PathVariable("userId")Long userId){

        User user = userRepository.findByIdx(userId);
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);


        Answer ans = new Answer();
        ans.setAnswer(200, "Success");
        return new ResponseEntity<>(bookmarks, HttpStatus.CREATED);
    }


    @PostMapping("api/bookmark")
    public ResponseEntity<Answer> uploadBookmark(@RequestBody BookmarkDto bookmarkDto){


        Bookmark bookmark = new Bookmark();

        bookmark.setBoard(boardRepository.findByIdx(bookmarkDto.getBoardId()));
        bookmark.setUser(userRepository.findByIdx(bookmarkDto.getUserId()));

        Bookmark chkBookmark = new Bookmark();
        User user = userRepository.findByIdx(bookmarkDto.getUserId());
        Board board = boardRepository.findByIdx(bookmarkDto.getBoardId());
        Answer ans = new Answer();

        if(bookmarkRepository.deleteGet(user, board) == null){
            bookmarkRepository.save(bookmark);
            ans.setAnswer(200, "Success");
            return new ResponseEntity<>(ans, HttpStatus.CREATED);

        }else{
            ans.setAnswer(400, "Fail(삭제부터 부탁드립니다)");
            return new ResponseEntity<>(ans, HttpStatus.EXPECTATION_FAILED);

        }


    }

    @DeleteMapping("api/bookmark/{userId}/{boardId}")
    public ResponseEntity<Answer> deleteBookmark(@PathVariable("userId")Long userId, @PathVariable("boardId")Long boardId ){


        User user = userRepository.findByIdx(userId);
        Board board = boardRepository.findByIdx(boardId);

        Bookmark bookmark = bookmarkRepository.deleteGet(user, board);

        System.out.println(bookmark.getIdx());
        bookmarkRepository.delete(bookmark);

        Answer ans = new Answer();
        ans.setAnswer(200, "Success");
        return new ResponseEntity<>(ans, HttpStatus.CREATED);
    }

}

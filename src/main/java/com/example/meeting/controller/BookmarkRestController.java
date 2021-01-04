package com.example.meeting.controller;

import com.example.meeting.entity.Board;
import com.example.meeting.dao.BoardRepository;
import com.example.meeting.dto.bookmark.Answer;
import com.example.meeting.dto.bookmark.BookmarkDto;
import com.example.meeting.dao.BookmarkRepository;
import com.example.meeting.entity.Bookmark;
import com.example.meeting.entity.User;
import com.example.meeting.dao.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class BookmarkRestController {
    private BookmarkRepository bookmarkRepository;
    private UserRepository userRepository;
    private BoardRepository boardRepository;

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
        bookmarkRepository.delete(bookmark);

        Answer ans = new Answer();
        ans.setAnswer(200, "Success");
        return new ResponseEntity<>(ans, HttpStatus.CREATED);
    }

}

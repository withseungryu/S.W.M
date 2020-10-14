package com.example.meeting.board;

import com.example.meeting.board.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RepositoryRestController
public class BoardRestController {

    private BoardRepository boardRepository;


    public BoardRestController(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @PostMapping(value = "/boards"  )
    public ResponseEntity<?> postBoard( @RequestParam Map<String, Object> body ) throws IOException {
        System.out.println(body);
        Board board = new Board();

        board.setTitle(body.get("title").toString());
        board.setGender(body.get("gender").toString());
        board.setNum_type(body.get("num_type").toString());
        board.setLocation(body.get("location").toString());
        board.setKeyword(body.get("keyword").toString());
//        board.setUser(body.get("user").toString());
        Object img1 = body.get("img1");
        Object img2 = body.get("img2");
        Object img3 = body.get("img3");


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(img1);
        byte[] yourBytes = bos.toByteArray();

        System.out.println(yourBytes);


        BufferedImage originalImage = ImageIO.read(new File("C://wait.jpg"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos);
        baos.flush();

//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ObjectOutputStream out = null;
//        byte[] yourBytes = null;
//        try {
//            out = new ObjectOutputStream(bos);
//            out.writeObject(body.get("img"));
//            out.flush();
//            yourBytes = bos.toByteArray();
//
//        } finally {
//            try {
//                bos.close();
//            } catch (IOException ex) {
//                // ignore close exception
//            }
//        }

        byte [] imageInByte = baos.toByteArray();
//
//        System.out.println(board.getImg1());
//        byte[] byImg1 = (byte[])body.get("img1");
//        byte[] byImg2 = img2.getBytes();
//        byte[] byImg3 = img3.getBytes();
////

        System.out.println();
//        for(int i=0; i<imageInByte.length; ++i){
//            System.out.println(imageInByte[i]  );
//        }
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(yourBytes);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            System.out.println(bufferedImage);
            ImageIO.write(bufferedImage, "jpg", new File("C://Users/alstm/Desktop/image1.jpg"));
            System.out.println("실행??3");
        }catch(IOException e){
            System.out.println("gdgd");
        }
//        board.setCreatedDateNow();
//        board.setUpdatedDateNow();
//        boardRepository.save(board);
        return new ResponseEntity<>("성공", HttpStatus.CREATED);
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

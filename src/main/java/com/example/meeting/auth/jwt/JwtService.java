package com.example.meeting.auth.jwt;

import com.example.meeting.entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtService{


    private String salt = "MYSALT";
    private Long expireMin = 5L;

    public String create(final User user){
        final JwtBuilder builder = Jwts.builder();

        builder.setHeaderParam("type", "JYT");

        builder.setSubject("로그인 토큰").setExpiration(new Date(System.currentTimeMillis()+1000*60*expireMin))
                .claim("User", user);

        builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

        final String jwt =builder.compact();
        return jwt;
    }

}
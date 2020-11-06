package com.example.meeting.jwt;

import com.example.meeting.user.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
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
package com.example.meeting.user;


import com.example.meeting.kakao_oauth.KakaoProfile;
import com.example.meeting.kakao_oauth.OAuthToken;
import com.example.meeting.kakao_oauth.Oauth;
import com.example.meeting.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserRestController {
    private UserRepository userRepository;


    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody
    String kakaoCallback(String code) { //@ResponseBody를 붙이는 이유 : Data를 리턴해주는 컨트롤러 간ㄷ된다.
        Oauth oauth= new Oauth(code);

        OAuthToken oauthToken = oauth.getOauthToken();

        oauth.getProfile(oauthToken, userRepository);

        return "완료";
    }
}

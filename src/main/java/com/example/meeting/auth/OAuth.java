//package com.example.meeting.kakao_oauth;
//
//import com.example.meeting.entity.User;
//import com.example.meeting.dao.UserRepository;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//public class OAuth {
//    private String code;
//
//    public OAuth(String code){
//        this.code =code;
//    }
//
//
//    public OAuthToken getOauthToken(){
//        String code = this.code;
//        //POST방식으로 key-value 데이터를 요청(카카오쪽으로)
//        RestTemplate rt = new RestTemplate(); //http 요청을 간단하게 해줄 수 있는 클래스
//
//        //HttpHeader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //HttpBody 오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type","authorization_code");
//        params.add("client_id","3a21998518680e26a7713430c60ac0a3");
//        params.add("redirect_uri","https://localhost:8080/auth/kakao/callback");
//        params.add("code", code);
//        params.add("client_secret", "gT8VS2oi38BgR7rKe6cTDOnEF8eu51dh");
//
//        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(params, headers);
//
//        //실제로 요청하기
//        //Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답을 받음.
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//
//        //Gson Library, JSON SIMPLE LIBRARY, OBJECT MAPPER(Check)
//        ObjectMapper objectMapper = new ObjectMapper();
//        OAuthToken oauthToken =null;
//        //Model과 다르게 되있으면 그리고 getter setter가 없으면 오류가 날 것이다.
//        try {
//            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return oauthToken;
//    }
//
//
//    public ResponseEntity<String> getProfile(OAuthToken oauthToken, UserRepository userRepository){
//        RestTemplate rt = new RestTemplate(); //http 요청을 간단하게 해줄 수 있는 클래스
//
//        //HttpHeader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer "+ oauthToken.getAccess_token());
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//
//        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
//                new HttpEntity<>(headers);
//
//        //실제로 요청하기
//        //Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답을 받음.
//        ResponseEntity<String> response = rt.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoProfileRequest,
//                String.class
//        );
//
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        KakaoProfile profile  =null;
//
////        Model과 다르게 되있으면 그리고 getter setter가 없으면 오류가 날 것이다.
//        try {
//            profile = objectMapper.readValue(response.getBody(), KakaoProfile.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(profile.getKakao_account().getEmail());
//
//        System.out.println(profile.getId());
//        User user = new User();
//        user.setNickName(profile.getProperties().getNickname());
//        user.setEmail(profile.getKakao_account().getEmail());
//        user.setGender(profile.getKakao_account().getGender());
//        user.setAge(profile.getKakao_account().getAge_range());
////        user.setBirth(profile.getKakao_account().getBirthday());
//
//        userRepository.save(user);
//        System.out.println(profile);
//        return response;
//
//    }
//
//    public ResponseEntity<String> deleteProfile(OAuthToken oauthToken){
//        RestTemplate rt = new RestTemplate(); //http 요청을 간단하게 해줄 수 있는 클래스
//
//        //HttpHeader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer "+ oauthToken.getAccess_token());
////        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//
//        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
//                new HttpEntity<>(headers);
//
//        //실제로 요청하기
//        //Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답을 받음.
//        ResponseEntity<String> response = rt.exchange(
//                "https://kapi.kakao.com//v1/user/unlink",
//                HttpMethod.POST,
//                kakaoProfileRequest,
//                String.class
//        );
//
//        System.out.println(response);
//        return response;
//    }
//}

//package com.example.meeting.fcmserver.service;
//
//import java.util.ArrayList;
//import java.util.concurrent.CompletableFuture;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//// firebase_server_key = firebase project > cloud messaging > server key
//
//@Service
//public class AndroidPushNotificationService {
//    private static final String firebase_server_key="AAAAhBAozFY:APA91bGeABT4lwHoQe8CY9b4VQjOnLNIYiNOFTEmyISdZN7mbm98GPxBIrI0slctsnPBYKJAZqZj1OOweIc_pGMfSkGTvIfrHnYAX_MCiSMYqHKhwirVCgR3-7_4nQTk0Mp0pXZ_pGjZ";
//    private static final String firebase_api_url="https://fcm.googleapis.com/fcm/send";
//
//    @Async
//    public CompletableFuture<String> send(HttpEntity<String> entity) {
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
//
//        interceptors.add(new HeaderRequestInterceptor("Authorization",  "key=" + firebase_server_key));
//        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json; UTF-8 "));
//        restTemplate.setInterceptors(interceptors);
//
//        String firebaseResponse = restTemplate.postForObject(firebase_api_url, entity, String.class);
//
//        return CompletableFuture.completedFuture(firebaseResponse);
//    }
//}
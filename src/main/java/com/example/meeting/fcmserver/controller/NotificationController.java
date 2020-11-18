package com.example.meeting.fcmserver.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.example.meeting.fcmserver.TokenDto;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.meeting.fcmserver.service.AndroidPushNotificationService;
import com.example.meeting.fcmserver.service.AndroidPushPeriodicNotifications;

@RestController
public class NotificationController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AndroidPushNotificationService androidPushNotificationsService;

    @PostMapping(value = "/api/send")
    public @ResponseBody ResponseEntity<String> send(@RequestBody TokenDto tokenDto) throws JSONException, InterruptedException  {
        String notifications = AndroidPushPeriodicNotifications.PeriodicNotificationJson(tokenDto);


        System.out.println(tokenDto.getToken1() + " ,,,, " +  tokenDto.getToken2());
        HttpEntity<String> request = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try{
            String firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        }
        catch (InterruptedException e){
            logger.debug("got interrupted!");
            throw new InterruptedException();
        }
        catch (ExecutionException e){
            logger.debug("execution error!");
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
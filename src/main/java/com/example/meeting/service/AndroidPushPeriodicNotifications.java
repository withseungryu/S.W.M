package com.example.meeting.service;

import com.example.meeting.dto.fcm.TokenDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AndroidPushPeriodicNotifications {

    public static String PeriodicNotificationJson(TokenDto tokenDto) throws JSONException, UnsupportedEncodingException {
        LocalDate localDate = LocalDate.now();

        String token1 = tokenDto.getToken1();
        String token2 = tokenDto.getToken2();

        String sampleData[] = {token1,token2};

        JSONObject body = new JSONObject();

        List<String> tokenlist = new ArrayList<String>();

        for(int i=0; i<sampleData.length; i++){
            tokenlist.add(sampleData[i]);
        }

        JSONArray array = new JSONArray();

        for(int i=0; i<tokenlist.size(); i++) {
            array.put(tokenlist.get(i));
        }

        body.put("registration_ids", array);

        JSONObject notification = new JSONObject();
        notification.put("title", URLEncoder.encode("Shall We Meet", "EUC-KR"));
        notification.put("body",URLEncoder.encode("Matched!!, Thank you for checking this!.", "EUC-KR"));

        body.put("notification", notification);


        return body.toString();
    }
}

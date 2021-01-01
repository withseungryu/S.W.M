package com.example.meeting.service;


import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CertificationService {
    public String certifiedPhoneNumber(String phoneNumber, String cerNum) {

        String api_key = "NCS7DJATSWSJEO0S";
        String api_secret = "AWMWTPGJEY2NJZOV4DRVIPZO59HRFTOX";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();


        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "01034353065");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨

        params.put("text", "미팅으로 만난 사이입니다. \n 인증번호는" + "["+cerNum+"]" + "입니다.");
        params.put("type", "sms");
//        params.put("app_version", "test app 1.2"); // application name and version


        try {
            JSONObject obj =  coolsms.send(params);
            System.out.println(obj.toString());
            System.out.println("일단 성공??");
            return "success";
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
            return "fail";
        }

    }
}

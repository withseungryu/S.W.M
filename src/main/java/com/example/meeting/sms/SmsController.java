package com.example.meeting.sms;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class SmsController {


    private CertificationService certificationService;
    private PhoneRepository phoneRepository;

    @GetMapping("/check/sendSMS")
    public @ResponseBody
    ResponseEntity<Answer> sendSMS(@RequestParam(value="phone") String phoneNumber) {

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        System.out.println("수신자 번호 : " + phoneNumber);
        System.out.println("인증번호 : " + numStr);
        Phone phone = new Phone(phoneNumber, numStr);
        phoneRepository.save(phone);
        certificationService.certifiedPhoneNumber(phoneNumber,numStr);
        Answer ans = new Answer();
        ans.setCode(200);
        ans.setMessage("Success");
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    @GetMapping("/check/checkSMS")
    public @ResponseBody
    ResponseEntity<Answer> checkSMS(@RequestParam(value="token") String token){

        Phone phone = phoneRepository.findByToken(token);
        Answer ans = new Answer();
        if( phone != null) {

            ans.setCode(200);
            ans.setMessage("Success");
        }
        else{
            ans.setCode(400);
            ans.setMessage("Failed");
        }
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

}



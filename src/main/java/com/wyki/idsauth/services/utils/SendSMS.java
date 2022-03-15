package com.wyki.idsauth.services.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service @Slf4j
public class SendSMS {

    @Value("internal.sendsms.endpoint")
    String smsendpoint;
    @Value("internal.sendsms.username")
    String smsusername;
    @Value("internal.sendsms.password")
    String smspassword;


    RestTemplate restTemplate = new RestTemplate();


    @Async
    public void sendSMS(String message, String phonenumber) {
        log.info("send text to "+phonenumber);
        restTemplate.getForEntity(smsendpoint+"sms?action=sendmessage&username="+smsusername+"&password="
                +smspassword+"&recipient="+phonenumber+"&messagedata="+message, String.class);
    }
}

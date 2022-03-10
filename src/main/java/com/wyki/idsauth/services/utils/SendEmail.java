package com.wyki.idsauth.services.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service @Slf4j
public class SendEmail {
    @Autowired
    JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String email,String message,String subject){
        log.debug("sending email to "+email);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject(subject);
        msg.setText(message);

        javaMailSender.send(msg);
    }
}

package com.duong.springdemoresful.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailSender mailSender;
    public void sendEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vanduonghaha12@gmail.com");
        message.setTo("vanduonghaha12@gmail.com");
        message.setSubject("Test Email");
        message.setText("This is the test email");
        mailSender.send(message);
    }
}

package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    @GetMapping("/emails")
    @Scheduled(cron= "*/30 * * * * *")
    public ResponseEntity<ApiResponse<String>> sendEmail(){
        emailService.sendEmailFromTemplateSync("vanduonghaha12@gmail.com","Nice Job","job");
        return ApiResponse.success(null,"Send email successfully!");
    }
}

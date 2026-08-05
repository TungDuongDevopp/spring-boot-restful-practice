
package com.duong.springdemoresful.controller;
import com.duong.springdemoresful.config.JwtConfig;
import com.duong.springdemoresful.helper.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class HomeController {

    private final JwtConfig jwtConfig;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<String>> index(){
        return ApiResponse.success(jwtConfig.createAccessToken());
    }
}

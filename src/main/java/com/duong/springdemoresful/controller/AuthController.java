
package com.duong.springdemoresful.controller;
import com.duong.springdemoresful.config.JwtService;
import com.duong.springdemoresful.dto.request.LoginRequest;
import com.duong.springdemoresful.dto.response.LoginResponse;
import com.duong.springdemoresful.helper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);

        String accessToken = jwtService.createAccessToken(authentication);

        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setUser(new LoginResponse.UserLogin(
                authentication.getName(), jwtService.getScope(authentication)
        ));
        return ApiResponse.success(response);
    }
}

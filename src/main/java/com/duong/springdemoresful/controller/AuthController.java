
package com.duong.springdemoresful.controller;
import com.duong.springdemoresful.config.JwtConfig;
import com.duong.springdemoresful.dto.request.LoginRequest;
import com.duong.springdemoresful.dto.response.LoginResponse;
import com.duong.springdemoresful.helper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtConfig jwtConfig;

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = daoAuthenticationProvider.authenticate(authToken);

        String accessToken = jwtConfig.createAccessToken(authentication);

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setUser(new LoginResponse.UserLogin(
                authentication.getName(),scope
        ));
        return ApiResponse.success(response);
    }
}

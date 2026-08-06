
package com.duong.springdemoresful.controller;
import com.duong.springdemoresful.config.JwtService;
import com.duong.springdemoresful.dto.request.LoginRequest;
import com.duong.springdemoresful.dto.response.ExchangeTokenResponse;
import com.duong.springdemoresful.dto.response.LoginResponse;
import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);
        User currentUser = userService.getUserByEmail(authentication.getName());

        String accessToken = jwtService.createAccessTokenAuth(authentication,currentUser.getId());

        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setUser(new LoginResponse.UserLogin(
                currentUser.getId(),
                authentication.getName(),
                jwtService.getScope(authentication)
        ));
        response.setRefreshToken(jwtService.createRefreshToken(currentUser));
        return ApiResponse.success(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<ExchangeTokenResponse>> postRefreshToken(@RequestParam("token") String refreshToken){
        return ApiResponse.success(jwtService.handleExchangeToken(refreshToken));

    }
}

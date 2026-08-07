
package com.duong.springdemoresful.controller;
import com.duong.springdemoresful.config.JwtService;
import com.duong.springdemoresful.dto.request.LoginRequest;
import com.duong.springdemoresful.dto.response.ExchangeTokenResponse;
import com.duong.springdemoresful.dto.response.LoginResponse;
import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.model.RefreshToken;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.service.RefreshTokenService;
import com.duong.springdemoresful.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    @Value("${duong.jwt.refresh-token-validity-in-seconds}")
    private String refreshTokenExpiration;


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
        String refreshToken = jwtService.createRefreshToken(currentUser);
        response.setRefreshToken(refreshToken);

        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Long.parseLong(refreshTokenExpiration))
                .build();
        ApiResponse<LoginResponse> finalData = new ApiResponse<>(HttpStatus.OK,"",response,"");


        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(finalData);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<ExchangeTokenResponse>> postRefreshToken(@RequestParam("token") String refreshToken){
        return ApiResponse.success(jwtService.handleExchangeToken(refreshToken));

    }
    @PostMapping("/refresh-with-cookie")
    public ResponseEntity<ApiResponse<ExchangeTokenResponse>> refreshWithCookie(
            @CookieValue(value = "refresh_token") String refreshToken) {

        ExchangeTokenResponse exchangeTokenResponse =
                jwtService.handleExchangeToken(refreshToken);

        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", exchangeTokenResponse.getRefreshToken())
                .httpOnly(true)
                .secure(false) // localhost
                .path("/")
                .maxAge(Long.parseLong(refreshTokenExpiration))
                .build();

        ApiResponse<ExchangeTokenResponse> finalData = new ApiResponse<>(HttpStatus.OK,"Login Sucessful",exchangeTokenResponse,null);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(finalData);
    }
    @GetMapping("/account")
    public ResponseEntity<ApiResponse<LoginResponse.UserLogin>> getAccount(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt)auth.getPrincipal();
        String userId = jwt.getClaimAsString("id");
        String username = jwt.getSubject();
        String role = jwt.getClaimAsString("scope");
        LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin();
        userLogin.setId(Long.parseLong(userId));
        userLogin.setRole(role);
        userLogin.setUsername(username);
        return  ApiResponse.success(userLogin);
    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>>logout(@AuthenticationPrincipal Jwt jwt,
                         @CookieValue(value = "refresh_token",required = false) String refreshToken){
        RefreshToken currentToken = refreshTokenService.findByToken(refreshToken);
        refreshTokenService.deleteTokenById(currentToken.getId());

        ResponseCookie deleteCokie= ResponseCookie.from("refresh_token", null)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();
        ApiResponse<String> finalData = new ApiResponse<>(HttpStatus.OK,"","ok","");

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,deleteCokie.toString())
                .body(finalData);

    }
}

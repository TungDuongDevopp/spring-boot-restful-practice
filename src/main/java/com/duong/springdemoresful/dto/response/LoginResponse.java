package com.duong.springdemoresful.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;

    private String refreshToken;

    private String tokenType = "Bearer";

    private UserLogin user;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLogin{
        private Long id;
        private String username;
        private String role;
    }
}

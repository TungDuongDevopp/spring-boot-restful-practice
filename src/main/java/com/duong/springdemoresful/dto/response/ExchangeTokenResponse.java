package com.duong.springdemoresful.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeTokenResponse {

    private String accessToken;

    private String refreshToken;

    private String token = "Bearer";

    private LoginResponse.UserLogin user;
}

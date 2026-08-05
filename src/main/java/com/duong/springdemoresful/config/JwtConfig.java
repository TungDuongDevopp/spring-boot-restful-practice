package com.duong.springdemoresful.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JwtConfig {
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS256;
    private final JwtEncoder jwtEncoder;

    @Value("${duong.jwt.access-token-validity-in-seconds}")
    private String accessTokenExpiration;

    public String createAccessToken(){

        //Tạo header
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        Instant now = Instant.now();
        //Tính time hết hạn
        Instant validity = now.plus(Long.parseLong(accessTokenExpiration), ChronoUnit.SECONDS);


        //Tạo payload
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject("duongdeptrai")
                .claim("duong","duong@gmail.com")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

    }
}

package com.duong.springdemoresful.config;

import com.duong.springdemoresful.dto.response.ExchangeTokenResponse;
import com.duong.springdemoresful.dto.response.LoginResponse;
import com.duong.springdemoresful.helper.exception.ResourceNotFoundException;
import com.duong.springdemoresful.model.RefreshToken;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS256;
    public static final Instant NOW = Instant.now();

    private final JwtEncoder jwtEncoder;
    private final RefreshTokenService refreshTokenService;

    @Value("${duong.jwt.access-token-validity-in-seconds}")
    private String accessTokenExpiration;


    @Value("${duong.jwt.refresh-token-validity-in-seconds}")
    private String refreshTokenExpiration;


    public String getScope(Authentication auth){
        return auth != null ? auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" ")) : "UNKNOWN";
    }

    public String createAccessToken(Long id, String username,String role){
        Instant validityAccessToken = NOW.plus(Long.parseLong(accessTokenExpiration), ChronoUnit.SECONDS);
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuedAt(NOW)
                .expiresAt(validityAccessToken)
                .subject(username)
                .claim("id",id)
                .claim("scope",role)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,claimsSet)).getTokenValue();
    }

    public String createAccessTokenAuth(Authentication auth, Long id){

      String scope = getScope(auth);
      return createAccessToken(id,auth.getName(),scope);
    }

    public String generateSecureToken() {
        byte[] randomBytes = new byte[64]; // 512 bits
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public String createRefreshToken(User user){
        Instant validityRefreshToken = NOW.plus(Long.parseLong(refreshTokenExpiration), ChronoUnit.SECONDS);
        RefreshToken refreshToken = new RefreshToken();
        String token = generateSecureToken();
        refreshToken.setCreatedAt(NOW);
        refreshToken.setExpiredAt(validityRefreshToken);
        refreshToken.setToken(token);
        refreshToken.setUser(user);
        refreshTokenService.createRefreshToken(refreshToken);
        return token;
    }

    public ExchangeTokenResponse handleExchangeToken(String token){
        RefreshToken refreshToken = refreshTokenService.findByToken(token);

        if(NOW.isAfter(refreshToken.getExpiredAt())){
            throw new ResourceNotFoundException("Token expired");
        }
        User currentUser = refreshToken.getUser();
        String newRefreshToken = createRefreshToken(currentUser);
        String scope = "ROLE_" + currentUser.getRole().getName();

        String accessToken = createAccessToken(currentUser.getId(),currentUser.getEmail(),scope);

        ExchangeTokenResponse exchangeTokenResponse  = new ExchangeTokenResponse();
        exchangeTokenResponse.setAccessToken(accessToken);
        exchangeTokenResponse.setRefreshToken(newRefreshToken);
        exchangeTokenResponse.setUser(new LoginResponse.UserLogin(currentUser.getId(),currentUser.getEmail(),scope));
        refreshTokenService.deleteTokenById(refreshToken.getId());
        return exchangeTokenResponse;

    }

}

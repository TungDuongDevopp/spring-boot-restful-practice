package com.duong.springdemoresful.service;

import com.duong.springdemoresful.helper.exception.ResourceNotFoundException;
import com.duong.springdemoresful.model.RefreshToken;
import com.duong.springdemoresful.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository repository;

    public void createRefreshToken(RefreshToken refreshToken){
        repository.save(refreshToken);
    }

    public RefreshToken findByToken(String refreshToken){
        return repository.findByToken(refreshToken).orElseThrow(
                ()->new ResourceNotFoundException("RefreshToken not found")
        );
    }
    public  void deleteTokenById(Long id){
        repository.deleteById(id);
    }
}

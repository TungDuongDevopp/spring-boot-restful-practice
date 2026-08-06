package com.duong.springdemoresful.config;

import com.duong.springdemoresful.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetailService implements UserDetailsService {

    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String username)  {

        com.duong.springdemoresful.model.User myUser = service.getUserByEmail(username);

        return User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .authorities("ROLE_"+myUser.getRole().getName())
                .build();


    }
}

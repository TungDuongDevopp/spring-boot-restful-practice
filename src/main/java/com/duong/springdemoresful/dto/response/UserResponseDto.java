package com.duong.springdemoresful.dto.response;


import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class UserResponseDto {

    private Long id;

    private String name;

    private String email;

    private String address;

    private RoleResponseDto role;

}

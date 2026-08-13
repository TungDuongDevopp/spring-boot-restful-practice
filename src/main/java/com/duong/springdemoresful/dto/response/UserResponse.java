package com.duong.springdemoresful.dto.response;


import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode


public class UserResponse {

    private Long id;

    private String name;

    private String email;

    private String address;

    private RoleResponse role;

}

package com.duong.springdemoresful.dto.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserFilterRequest {

    private String name;

    private String address;

    private String email;

    private String roleName;
}

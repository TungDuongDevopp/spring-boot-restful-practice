package com.duong.springdemoresful.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class RoleResponse {

    private Long id;

    private String name;
}

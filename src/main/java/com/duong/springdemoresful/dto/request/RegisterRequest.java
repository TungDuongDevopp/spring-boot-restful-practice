package com.duong.springdemoresful.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "name không được trống")
    private String name;

    @NotBlank(message = "email không được trống")
    private String email;

    @NotBlank(message = "address không được trống")
    private String address;
    @NotBlank(message = "password không được trống")
    private String password;
}

package com.duong.springdemoresful.dto.request;

import com.duong.springdemoresful.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestUpdateDto {

    private Long id;

    @NotBlank(message = "name không được để trống")
    private String name;

    @NotBlank(message = "address không được để trống")
    private String address;

    private Role role;
}

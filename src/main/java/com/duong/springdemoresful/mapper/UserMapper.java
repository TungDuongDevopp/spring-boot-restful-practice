package com.duong.springdemoresful.mapper;

import com.duong.springdemoresful.dto.request.RegisterRequest;
import com.duong.springdemoresful.dto.request.UserRequestCreate;
import com.duong.springdemoresful.dto.request.UserRequestUpdate;
import com.duong.springdemoresful.dto.response.UserResponse;
import com.duong.springdemoresful.model.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntityCreate(UserRequestCreate userRequestCreate);

    User toEntityRegister(RegisterRequest registerRequest);

    User toEntityUpdate(UserRequestUpdate userRequestUpdate);

    UserResponse toResponse(User user);

}

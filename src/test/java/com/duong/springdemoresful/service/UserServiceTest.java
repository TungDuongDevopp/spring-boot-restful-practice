package com.duong.springdemoresful.service;

import com.duong.springdemoresful.dto.request.UserRequestCreate;
import com.duong.springdemoresful.dto.response.RoleResponse;
import com.duong.springdemoresful.dto.response.UserResponse;
import com.duong.springdemoresful.helper.exception.DuplicateResourceException;
import com.duong.springdemoresful.helper.exception.ResourceNotFoundException;
import com.duong.springdemoresful.mapper.UserMapper;
import com.duong.springdemoresful.model.Role;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.repository.RoleRepository;
import com.duong.springdemoresful.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private  UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUser_shouldReturnUser_whenEmailValid() {
        Role role = new Role(1L,"ADMIN","Quyền ADMIN",null);
        Long roleId = role.getId();
        String roleName = role.getName();
        UserRequestCreate inputUserDto = new UserRequestCreate(null,"Duong","admin@gmail.com","123456","HaNoi",role);
        UserResponse outputUserDto = new UserResponse(1L,"Duong","admin@gmail.com","HaNoi",new RoleResponse(roleId,roleName));
        String hashedPassword = encoder.encode("123456");

        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setName(inputUserDto.getName());
        userEntity.setEmail(inputUserDto.getEmail());
        userEntity.setPassword(hashedPassword);
        userEntity.setAddress(inputUserDto.getAddress());
        userEntity.setRole(role);

        when(userRepository.existsByEmail(inputUserDto.getEmail())).thenReturn(false);
        when(roleRepository.findByIdOrName(roleId,roleName)).thenReturn(Optional.of(role));
        when(encoder.encode("123456")).thenReturn(hashedPassword);
        when(userMapper.toEntityCreate(inputUserDto)).thenReturn(userEntity);
        when(userRepository.save(any(User.class))).thenReturn(userEntity);
        when(userMapper.toResponse(userEntity)).thenReturn(outputUserDto);

        UserResponse userResponse = userService.createUser(inputUserDto);

        assertEquals(outputUserDto,userResponse);

    }
    @Test
    public void createUser_shouldThrowException_whenEmailDuplicate() {
        Role role = new Role(1L,"ADMIN","Quyền ADMIN",null);
        UserRequestCreate inputUserDto = new UserRequestCreate(null,"Duong","admin@gmail.com","123456","HaNoi",role);;
        when(userRepository.existsByEmail(inputUserDto.getEmail())).thenReturn(true);

        Exception ex = assertThrows(DuplicateResourceException.class,()->{
            userService.createUser(inputUserDto);
        });
         assertEquals("Email already exists",ex.getMessage());

    }
    @Test
    public void createUser_shouldThrowException_whenRoleInvalid() {
        Role role = new Role(1L,"ADMIN","Quyền ADMIN",null);
        Long roleId = role.getId();
        String roleName = role.getName();
        UserRequestCreate inputUserDto = new UserRequestCreate(null,"Duong","admin@gmail.com","123456","HaNoi",role);

        when(userRepository.existsByEmail(inputUserDto.getEmail())).thenReturn(false);
        when(roleRepository.findByIdOrName(roleId,roleName)).thenReturn(Optional.empty());

        Exception ex = assertThrows(ResourceNotFoundException.class,()->{
            userService.createUser(inputUserDto);
        });
        assertEquals("Role not found",ex.getMessage());

    }

}

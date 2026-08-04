
package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.dto.request.UserRequestCreateDto;
import com.duong.springdemoresful.dto.request.UserRequestUpdateDto;
import com.duong.springdemoresful.dto.response.UserResponseDto;
import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<UserResponseDto>>createUser(@Valid @RequestBody UserRequestCreateDto user){
		UserResponseDto createUser = userService.createUser(user);
		return ApiResponse.created(createUser);
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResponse<List<UserResponseDto>>>getAllUsers(){
        List<UserResponseDto> users = userService.fetchUsers();
		return ApiResponse.success(users);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>>getUserById(@PathVariable Long id){

		UserResponseDto user = userService.findUserById(id);
		return ApiResponse.success(user);
	}

	@PutMapping ("/users/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>>updateUserById(@PathVariable Long id, @Valid @RequestBody UserRequestUpdateDto inputUser){
		UserResponseDto updateUser = userService.updateUser(inputUser,id);

		return ApiResponse.success(updateUser);
	}

	@DeleteMapping ("/users/{id}")
	public ResponseEntity<ApiResponse<Void>>deleteUserById(@PathVariable Long id){
		userService.deleteUserById(id);
		return ApiResponse.success(null,"Deleted successfully");
	}

}

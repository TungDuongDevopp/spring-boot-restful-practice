
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

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<UserResponseDto>>createUser(@Valid @RequestBody UserRequestCreateDto user){
		return ApiResponse.created(userService.createUser(user));
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResponse<List<UserResponseDto>>>getAllUsers(@RequestParam(required = false) String role){
		List<UserResponseDto> users;
		if(role !=null){
			users = userService.fetchUsersByRole(role);
		}
		else{
			users = userService.fetchUsers();
		}

		return ApiResponse.success(users);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>>getUserById(@PathVariable Long id){
		return ApiResponse.success( userService.findUserById(id));
	}

	@PutMapping ("/users/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>>updateUserById(@PathVariable Long id, @Valid @RequestBody UserRequestUpdateDto inputUser){
		return ApiResponse.success(userService.updateUser(inputUser,id));
	}

	@DeleteMapping ("/users/{id}")
	public ResponseEntity<ApiResponse<Void>>deleteUserById(@PathVariable Long id){
		userService.deleteUserById(id);
		return ApiResponse.success(null,"Deleted successfully");
	}

}

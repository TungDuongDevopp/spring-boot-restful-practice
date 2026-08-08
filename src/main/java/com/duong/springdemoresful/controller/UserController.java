
package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.dto.request.UserFilterRequest;
import com.duong.springdemoresful.dto.request.UserRequestCreate;
import com.duong.springdemoresful.dto.request.UserRequestUpdate;
import com.duong.springdemoresful.dto.response.UserResponse;
import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.helper.PageResponse;

import com.duong.springdemoresful.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<UserResponse>>createUser(@Valid @RequestBody UserRequestCreate user){
		return ApiResponse.created(userService.createUser(user));
	}


	@GetMapping("/users")
	public ResponseEntity<ApiResponse<PageResponse<UserResponse>>>getAllUsers(UserFilterRequest filterRequest,
	        Pageable pageable){

 	Page<UserResponse> users= userService.fetchUsers(pageable,filterRequest);
		return ApiResponse.success(PageResponse.from(users));
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<UserResponse>>getUserById(@PathVariable Long id){
		return ApiResponse.success( userService.findUserById(id));
	}

	@PutMapping ("/users/{id}")
	public ResponseEntity<ApiResponse<UserResponse>>updateUserById(@PathVariable Long id, @Valid @RequestBody UserRequestUpdate inputUser){
		return ApiResponse.success(userService.updateUser(inputUser,id));
	}

	@DeleteMapping ("/users/{id}")
	public ResponseEntity<ApiResponse<Void>>deleteUserById(@PathVariable Long id){
		userService.deleteUserById(id);
		return ApiResponse.success(null,"Deleted successfully");
	}

}

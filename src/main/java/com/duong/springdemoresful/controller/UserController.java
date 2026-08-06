
package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.dto.request.UserRequestCreate;
import com.duong.springdemoresful.dto.request.UserRequestUpdate;
import com.duong.springdemoresful.dto.response.UserResponse;
import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<UserResponse>>createUser(@Valid @RequestBody UserRequestCreate user){
		return ApiResponse.created(userService.createUser(user));
	}


	@GetMapping("/users")
	public ResponseEntity<ApiResponse<List<UserResponse>>>getAllUsers(@RequestParam(required = false) String role){
		List<UserResponse> users;
		if(role !=null){
			users = userService.fetchUsersByRole(role);
		}
		else{
			users = userService.fetchUsers();
		}

		return ApiResponse.success(users);
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

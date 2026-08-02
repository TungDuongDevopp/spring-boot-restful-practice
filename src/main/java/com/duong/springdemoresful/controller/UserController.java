
package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}



	@PostMapping("/users")
	public ResponseEntity<ApiResponse<User>>createUser(@RequestBody User user){
		User createUser = userService.createUser(user);
		return ApiResponse.created(createUser);
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResponse<List<User>>>getAllUsers(){
        List<User> users = userService.fetchUsers();
		return ApiResponse.success(users);
	}



	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>>getUserById(@PathVariable int id){

		User user = userService.findUserById(id);
		return ApiResponse.success(user);
	}

	@PutMapping ("/users/{id}")
	public ResponseEntity<ApiResponse<User>>updateUserById(@PathVariable int id, @RequestBody User inputUser){
		inputUser.setId(id);
		User updateUser = userService.updateUser(inputUser);

		return ApiResponse.success(updateUser);
	}

	@DeleteMapping ("/users/{id}")
	public ResponseEntity<ApiResponse<Boolean>>deleteUserById(@PathVariable int id){
		boolean status = userService.deleteUserById(id);
		return ApiResponse.success(status);
	}

}

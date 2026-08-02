
package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.service.UserService;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<User>createUser(@RequestBody User user){
		User createUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>>getAllUsers(){
        List<User> users = userService.fetchUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User>getUserById(@PathVariable int id){
		User user = userService.findUserById(id);
		return ResponseEntity.ok(user);
	}

	@PutMapping ("/users/{id}")
	public ResponseEntity<User>updateUserById(@PathVariable int id, @RequestBody User inputUser){
		inputUser.setId(id);
		User updateUser = userService.updateUser(inputUser);

		return ResponseEntity.ok(updateUser);
	}

	@DeleteMapping ("/users/{id}")
	public ResponseEntity<Boolean>deleteUserById(@PathVariable int id){
		boolean status = userService.deleteUserById(id);
		return ResponseEntity.ok(status);
	}





}

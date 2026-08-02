
package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class HomeController {

	private final UserService userService;

	public HomeController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String index() {
		return "Hello World from Spring Boot - @hoidanit devtool: " + "Dương";
	}
	@GetMapping("/Duong")
	public ResponseEntity<String> demoAPI(){
		//return  new ResponseEntity<String>("Hello World", HttpStatus.CREATED);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Hello World");
	}
}

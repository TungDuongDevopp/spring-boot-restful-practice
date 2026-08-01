
package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class HomeController {

	private final UserService userService;

	public HomeController(UserService userService) {
		this.userService = userService;
	}

	@Value("${hoidanit.secret:default-value}")
	private String name;

	@GetMapping("/")
	public String index() {

		return "Hello World from Spring Boot - @hoidanit devtool: " + name;
	}
}

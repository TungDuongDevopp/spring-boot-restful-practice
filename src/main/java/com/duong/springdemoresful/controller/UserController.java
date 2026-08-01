
package com.duong.springdemoresful.controller;

import java.util.List;

import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;



@Controller
public class UserController {

	// unit test
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/hoidanit")
	public String eric(Model model) {

		// gọi service, gọi database => lấy data
		model.addAttribute("name", "hoidanit with eric"); // x <= y

		return "hello";
	}

	@GetMapping("/user")
	public String showUser(Model model) {

		List<User> userList = this.userService.fetchUsers();
		model.addAttribute("users", userList); // x <= y

		return "/user/show";
	}

	@GetMapping("/user/create")
	public String getCreatePage(Model model) {
		model.addAttribute("user", new User());
		return "/user/create";
	}

	@PostMapping("/user/create")
	public String postCreatePage(@Valid @ModelAttribute User createUser, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/user/create";
		}

		this.userService.createUser(createUser);
		return "redirect:/user";
	}

	@GetMapping("/user/{id}")
	public String getUpdateUserPage(Model model, @PathVariable int id) {
		// select * from user where id = input
		User updateUser = this.userService.findUserById(id);

		model.addAttribute("user", updateUser);
		model.addAttribute("id", id);
		return "/user/update";
	}

	@PostMapping("/user/update")
	public String postUpdatePage(@Valid @ModelAttribute User updateUser, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("user", updateUser);
			model.addAttribute("id", updateUser.getId());
			return "/user/update";
		}

		this.userService.updateUser(updateUser);
		return "redirect:/user";
	}

	@PostMapping("/user/delete/{id}")
	public String postDeleteUser(Model model, @PathVariable int id) {
		this.userService.deleteUserById(id);
		return "redirect:/user";
	}

	@GetMapping("/admin")
	public String showAdmin() {

		return "/admin/show";
	}
}

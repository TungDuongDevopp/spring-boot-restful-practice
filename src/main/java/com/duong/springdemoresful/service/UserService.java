
package com.duong.springdemoresful.service;


import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> fetchUsers() {

		List<User> userList = this.userRepository.findAll();

		return userList;
	}

	public void createUser(User user) {

		this.userRepository.save(user);

	}

	public User findUserById(int id) {
		Optional<User> userOpt = this.userRepository.findById(id);
		return userOpt.get();
	}

	public void updateUser(User inputUser) {
		User currentUserInDB = this.findUserById(inputUser.getId());
		if (currentUserInDB != null) {
			currentUserInDB.setName(inputUser.getName());
			currentUserInDB.setEmail(inputUser.getEmail());
			currentUserInDB.setAddress(inputUser.getAddress());

			this.userRepository.save(currentUserInDB);
		}
	}

	public void deleteUserById(int id) {
		this.userRepository.deleteById(id);
	}

}

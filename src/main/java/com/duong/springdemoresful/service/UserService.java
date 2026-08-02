
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

	public User createUser(User user) {

		return userRepository.save(user);

	}

	public User findUserById(int id) {
		Optional<User> userOpt = this.userRepository.findById(id);
		return userOpt.get();
	}

	public User updateUser(User inputUser) {
		User currentUserInDB = this.findUserById(inputUser.getId());
		if (currentUserInDB != null) {
			currentUserInDB.setName(inputUser.getName());
			currentUserInDB.setEmail(inputUser.getEmail());
			currentUserInDB.setAddress(inputUser.getAddress());
			 return this.userRepository.save(currentUserInDB);
		}
		return null;
	}

	public boolean deleteUserById(int id) {
		if(!userRepository.existsById(id)) return false;
		userRepository.deleteById(id);
		return true;
	}

}

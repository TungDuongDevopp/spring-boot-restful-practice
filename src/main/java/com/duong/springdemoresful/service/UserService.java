
package com.duong.springdemoresful.service;


import com.duong.springdemoresful.helper.ResourceNotFoundException;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public List<User> fetchUsers() {
		return this.userRepository.findAll();

	}

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public User findUserById(int id) {
		return this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));
	}

	public User updateUser(User inputUser) {

		User currentUser = findUserById(inputUser.getId()) ;
			currentUser.setName(inputUser.getName());
			currentUser.setEmail(inputUser.getEmail());
			currentUser.setAddress(inputUser.getAddress());
			 return this.userRepository.save(currentUser);

	}

	public boolean deleteUserById(int id) {
		if(!userRepository.existsById(id)) return false;
		userRepository.deleteById(id);
		return true;
	}

}

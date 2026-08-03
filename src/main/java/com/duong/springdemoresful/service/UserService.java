
package com.duong.springdemoresful.service;


import com.duong.springdemoresful.helper.ResourceNotFoundException;
import com.duong.springdemoresful.model.Tag;
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

	public User findUserById(Long id) {
		return this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));
	}

	public User updateUser(User inputUser,Long id) {

		User currentUser = findUserById(id) ;
			currentUser.setName(inputUser.getName());
			currentUser.setEmail(inputUser.getEmail());
			currentUser.setAddress(inputUser.getAddress());
			 return this.userRepository.save(currentUser);

	}

	public void deleteUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		userRepository.deleteById(id);
	}

}


package com.duong.springdemoresful.service;


import com.duong.springdemoresful.dto.UserResponseDto;
import com.duong.springdemoresful.helper.DuplicateResourceException;
import com.duong.springdemoresful.helper.ResourceNotFoundException;
import com.duong.springdemoresful.model.Role;
import com.duong.springdemoresful.model.Tag;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.repository.RoleRepository;
import com.duong.springdemoresful.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	private final RoleRepository roleRepository;

	public List<User> fetchUsers() {
		return this.userRepository.findAll();

	}

	public UserResponseDto createUser(User user) {
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new DuplicateResourceException("Email already exists");
		}
		Long roleId = user.getRole().getId();
		String roleName = user.getRole().getName();
		Role existsRole = roleRepository.findByIdOrName(roleId,roleName)
				.orElseThrow(()->new ResourceNotFoundException("Role not found"));

		String hashPassword = encoder.encode(user.getPassword());
		user.setPassword(hashPassword);
		user.setRole(existsRole);
		return convert(userRepository.save(user));
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

	public UserResponseDto convert(User user){

		return UserResponseDto.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.address(user.getAddress())
				.role(user.getRole())
				.build();

	}

	public void deleteUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		userRepository.deleteById(id);
	}

}

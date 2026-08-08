
package com.duong.springdemoresful.service;


import com.duong.springdemoresful.dto.request.UserRequestCreate;
import com.duong.springdemoresful.dto.response.RoleResponse;
import com.duong.springdemoresful.dto.request.UserRequestUpdate;
import com.duong.springdemoresful.dto.response.UserResponse;
import com.duong.springdemoresful.helper.exception.DuplicateResourceException;
import com.duong.springdemoresful.helper.exception.ResourceNotFoundException;
import com.duong.springdemoresful.model.Role;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.repository.RoleRepository;
import com.duong.springdemoresful.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@RequiredArgsConstructor
@Service

public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	private final RoleRepository roleRepository;

	public Page<UserResponse> fetchUsers(Pageable pageable) {
		return this.userRepository.findAll(pageable)
				.map(user-> UserResponse.builder()
						.id(user.getId())
						.name(user.getName())
						.email(user.getEmail())
						.role(new RoleResponse(user.getRole().getId(),user.getRole().getName()))
						.build());

	}
	public List<UserResponse> fetchUsersByRole(String role) {
		return this.userRepository.findByRole_Name(role).stream()
				.map(user-> UserResponse.builder()
						.id(user.getId())
						.name(user.getName())
						.email(user.getEmail())
						.role(new RoleResponse(user.getRole().getId(),user.getRole().getName()))
						.build()).toList();

	}

	public UserResponse createUser(UserRequestCreate userDto) {
		if(userRepository.existsByEmail(userDto.getEmail())) {
			throw new DuplicateResourceException("Email already exists");
		}

		Long roleId = userDto.getRole().getId();
		String roleName = userDto.getRole().getName();
		Role existsRole = roleRepository.findByIdOrName(roleId, roleName)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found"));

		String hashPassword = encoder.encode(userDto.getPassword());

		User userEntity = new User();
		userEntity.setName(userDto.getName());
		userEntity.setEmail(userDto.getEmail());
		userEntity.setPassword(hashPassword);
		userEntity.setRole(existsRole);

		return convert(userRepository.save(userEntity));
	}

	public UserResponse findUserById(Long id) {
		return convert(this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found")));
	}


	@Transactional
	public UserResponse updateUser(UserRequestUpdate inputUser, Long id) {

		User currentUser = userRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("User not found")
		);
		if(inputUser.getRole() != null){
			currentUser.setRole(inputUser.getRole());
		}
		currentUser.setName(inputUser.getName());
		currentUser.setAddress(inputUser.getAddress());
		return convert(currentUser);


	}

	public UserResponse convert(User user){

		return UserResponse.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.address(user.getAddress())
				.role(new RoleResponse(user.getRole().getId(),user.getRole().getName()))
				.build();

	}
	public User getUserByEmail(String email){
		return userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Customer not found"));
	}

	public void deleteUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		userRepository.deleteById(id);
	}

}

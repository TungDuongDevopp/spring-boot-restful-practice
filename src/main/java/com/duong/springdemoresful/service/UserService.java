
package com.duong.springdemoresful.service;


import com.duong.springdemoresful.dto.RoleResponseDto;
import com.duong.springdemoresful.dto.UserRequestDto;
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
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	private final RoleRepository roleRepository;

	public List<UserResponseDto> fetchUsers() {
		return this.userRepository.findAll().stream()
				.map(user->UserResponseDto.builder()
						.id(user.getId())
						.name(user.getName())
						.email(user.getEmail())
						.role(new RoleResponseDto(user.getRole().getId(),user.getRole().getName()))
						.build()).collect(Collectors.toList());

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

	public UserResponseDto findUserById(Long id) {
		return convert(this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found")));
	}

	public UserResponseDto updateUser(UserRequestDto inputUser, Long id) {

		User currentUser = userRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("User not found")
		);
		if(inputUser.getRole() != null){
			currentUser.setRole(inputUser.getRole());
		}
		currentUser.setName(inputUser.getName());
		currentUser.setAddress(inputUser.getAddress());

		return convert(userRepository.save(currentUser));


	}

	public UserResponseDto convert(User user){

		return UserResponseDto.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.address(user.getAddress())
				.role(new RoleResponseDto(user.getRole().getId(),user.getRole().getName()))
				.build();

	}

	public void deleteUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		userRepository.deleteById(id);
	}

}

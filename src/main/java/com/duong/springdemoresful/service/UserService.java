
package com.duong.springdemoresful.service;


import com.duong.springdemoresful.dto.request.UserRequestCreateDto;
import com.duong.springdemoresful.dto.response.RoleResponseDto;
import com.duong.springdemoresful.dto.request.UserRequestUpdateDto;
import com.duong.springdemoresful.dto.response.UserResponseDto;
import com.duong.springdemoresful.helper.DuplicateResourceException;
import com.duong.springdemoresful.helper.ResourceNotFoundException;
import com.duong.springdemoresful.model.Role;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.repository.RoleRepository;
import com.duong.springdemoresful.repository.UserRepository;
import jakarta.transaction.Transactional;
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
						.build()).toList();

	}
	public List<UserResponseDto> fetchUsersByRole(String role) {
		return this.userRepository.findByRole_Name(role).stream()
				.map(user->UserResponseDto.builder()
						.id(user.getId())
						.name(user.getName())
						.email(user.getEmail())
						.role(new RoleResponseDto(user.getRole().getId(),user.getRole().getName()))
						.build()).toList();

	}

	public UserResponseDto createUser(UserRequestCreateDto userDto) {
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

	public UserResponseDto findUserById(Long id) {
		return convert(this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found")));
	}


	@Transactional
	public UserResponseDto updateUser(UserRequestUpdateDto inputUser, Long id) {

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

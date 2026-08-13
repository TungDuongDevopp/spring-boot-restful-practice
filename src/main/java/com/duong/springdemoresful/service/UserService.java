
package com.duong.springdemoresful.service;


import com.duong.springdemoresful.dto.request.RegisterRequest;
import com.duong.springdemoresful.dto.request.UserFilterRequest;
import com.duong.springdemoresful.dto.request.UserRequestCreate;
import com.duong.springdemoresful.dto.response.RoleResponse;
import com.duong.springdemoresful.dto.request.UserRequestUpdate;
import com.duong.springdemoresful.dto.response.UserResponse;
import com.duong.springdemoresful.helper.exception.DuplicateResourceException;
import com.duong.springdemoresful.helper.exception.ResourceNotFoundException;
import com.duong.springdemoresful.mapper.UserMapper;
import com.duong.springdemoresful.model.Role;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.repository.RoleRepository;
import com.duong.springdemoresful.repository.UserRepository;
import com.duong.springdemoresful.service.specification.UserSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	private final RoleRepository roleRepository;
	private final UserMapper userMapper;

	public Page<UserResponse> fetchUsers(Pageable pageable, UserFilterRequest filterRequest) {
		Specification<User> specification = Specification.allOf(
				UserSpecification.hasName(filterRequest),
				UserSpecification.hasRole(filterRequest)
		);

		return userRepository.findAll(specification, pageable)
				.map(userMapper::toResponse);


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
		User userEntity = userMapper.toEntityCreate(userDto);
		userEntity.setPassword(hashPassword);
		userEntity.setRole(existsRole);
		return  userMapper.toResponse(userRepository.save(userEntity));
	}

	public UserResponse findUserById(Long id) {
		return userMapper.toResponse(this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found")));
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
		return userMapper.toResponse(currentUser);


	}

	public User getUserByEmail(String email){
		return userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Customer not found"));
	}

	public void deleteUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if(user.getEmail().equals("user@example.com")|| user.getEmail().equals("admin@example.com")){
			throw new ResourceNotFoundException("Can not delete this Account");
		}
		userRepository.deleteById(id);
	}
	public void registerUser(RegisterRequest request){
		if(userRepository.existsByEmail(request.getEmail())){
			throw new DuplicateResourceException("Email has exits");

		}
		Role userRole = roleRepository.findByIdOrName(null,"USER").orElseThrow(()-> new ResourceNotFoundException("Role not found"));
		User user = userMapper.toEntityRegister(request);
		String hashPassword = encoder.encode(request.getPassword());
		user.setPassword(hashPassword);
		userRepository.save(user);
	}

}

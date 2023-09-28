package com.project.urban.Service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.urban.DTO.LoginDTO;
import com.project.urban.DTO.UserDTO;
import com.project.urban.DTO.UserEditDTO;
import com.project.urban.Entity.User;
import com.project.urban.Exception.Constant;
import com.project.urban.Exception.ErrorConstant;
import com.project.urban.Exception.InvalidDataException;
import com.project.urban.Exception.ResourceNotFoundException;
import com.project.urban.Exception.ResponseCode;
import com.project.urban.Repository.UserRepository;
import com.project.urban.Service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDTO createUser(UserDTO userDTO) {

		// Mã hóa password
		String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(userDTO, User.class);
		user.setRole("Role_User");
		user.setPassword(hashedPassword); // Lưu password đã mã hóa
		User savedUser = userRepository.save(user);

		UserDTO savedUserDTO = modelMapper.map(savedUser, UserDTO.class);

		return savedUserDTO;
	}

	@Override
	public UserDTO getUserById(Long userId) {
		ModelMapper modelMapper = new ModelMapper();

		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<UserDTO> allUsers = new ArrayList<>();
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			throw new InvalidDataException(ErrorConstant.NOT_FOUND, ErrorConstant.USER_NOT_FOUND);
		}
		ModelMapper modelMapper = new ModelMapper();
		for (User user : users) {
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			allUsers.add(userDTO);
		}
		return allUsers;
	}

	@Override
	public UserEditDTO updateUser(UserEditDTO userEditDTO) {
		User existingUser = userRepository.findById(userEditDTO.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.map(userEditDTO, existingUser);

		User updatedUser = userRepository.save(existingUser);

		UserEditDTO updatedUserDTO = modelMapper.map(updatedUser, UserEditDTO.class);
		return updatedUserDTO;
	}

	@Override
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
		userRepository.deleteById(user.getId());
	}

	@Override
	public ResourceNotFoundException loginUser(LoginDTO loginDTO) {
		Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());
		if (user.isEmpty()) {
			return new ResourceNotFoundException(ResponseCode.CODE_404, ErrorConstant.NOT_FOUND, null);
		} else {
			User foundUser = user.get();
			if (foundUser.getPassword() != null) {
				boolean isPwdRight = passwordEncoder.matches(loginDTO.getPassword(), foundUser.getPassword());
				if (isPwdRight) {
					return new ResourceNotFoundException(ResponseCode.CODE_200, Constant.LOGIN_SUCCESS, user);
				}
			}
		}
		return new ResourceNotFoundException(ResponseCode.CODE_500, ErrorConstant.INTERNAL_SERVER_ERROR, null);
	}
}

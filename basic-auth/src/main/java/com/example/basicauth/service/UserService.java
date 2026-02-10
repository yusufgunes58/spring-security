package com.example.basicauth.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.basicauth.dto.CreateUserRequest;
import com.example.basicauth.modul.User;
import com.example.basicauth.repository.UserRepository;


@Service
public class UserService {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	public UserService(UserRepository userRep, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRep;
		this.passwordEncoder = passwordEncoder;
	}

	public Optional<User> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public User createUser(CreateUserRequest request) {
		User newUser = User.builder()
				.username(request.username())
				.password(passwordEncoder.encode(request.password()))
				.authorized(request.authorities())
				.accountNonExpired(true)
				.credentialsNonExpired(true)
				.isEnabled(true)
				.accountNonLocked(true)
				.build();
		
		return userRepository.save(newUser);
	}
	
}

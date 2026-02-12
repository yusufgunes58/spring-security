package com.security.jwt_token.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.jwt_token.dto.CreateUserRequest;
import com.security.jwt_token.model.User;
import com.security.jwt_token.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRep;
	private final BCryptPasswordEncoder passwordEncoder;
	public UserService(UserRepository userRep, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.userRep = userRep;
		this.passwordEncoder = passwordEncoder;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<com.security.jwt_token.model.User> user = userRep.findByUsername(username);
		return user.orElseThrow(EntityNotFoundException::new);
	}
	
	public User createUser(CreateUserRequest request) {
		
		 User newUser = User.builder()
	                .name(request.name())
	                .username(request.username())
	                .password(passwordEncoder.encode(request.password()))
	                .authorities(request.authorities())
	                .accountNonExpired(true)
	                .credentialsNonExpired(true)
	                .isEnabled(true)
	                .accountNonLocked(true)
	                .build();

		 return userRep.save(newUser);
	    
	}
	
	
	
	
}

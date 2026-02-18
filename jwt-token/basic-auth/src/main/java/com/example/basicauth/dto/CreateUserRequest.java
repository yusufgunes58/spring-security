package com.example.basicauth.dto;

import java.util.Set;

import com.example.basicauth.modul.Role;

import lombok.Builder;

@Builder
public record CreateUserRequest(
		
		String name,
		String username,
		String password,
		Set<Role> authorities
		) {

	
}

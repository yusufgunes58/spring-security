package com.example.basicauth;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.basicauth.dto.CreateUserRequest;
import com.example.basicauth.modul.Role;
import com.example.basicauth.service.UserService;

@SpringBootApplication
public class BasicauthApplication  implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(BasicauthApplication.class, args);
	}

	private final UserService userService;
	
	
	public BasicauthApplication(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		createDummyData();
		
	}

	private void createDummyData() {
		CreateUserRequest request = CreateUserRequest.builder() 
				.name("Emin")
				.username("emin")
				.password("pass")
				.authorities(Set.of(Role.ROLE_USER))
				.build();
		
		userService.createUser(request);

		
	CreateUserRequest request1= CreateUserRequest.builder()
			.name("FSK")
			.username("fsk")
			.password("pass")
			.authorities(Set.of(Role.ROLE_FSK))
			.build();
	
	userService.createUser(request1);
	
	
	CreateUserRequest request3 = CreateUserRequest.builder()
			.name("yusyf")
			.username("noname")
			.password("pass")
			.authorities(Set.of(Role.ROLE_ADMIN))
			.build();
	
	userService.createUser(request3);
	
	}
	
}

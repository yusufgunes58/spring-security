package com.example.basicauth.security;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.example.basicauth.modul.Role;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		
		 security
	        .headers(headers -> headers.frameOptions(frame -> frame.disable()))
	        .csrf(csrf -> csrf
	                .ignoringRequestMatchers("/public/**")
	                .ignoringRequestMatchers(PathRequest.toH2Console()))
	        .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/public/**").permitAll()
	                .requestMatchers("/private/admin/**").hasAnyRole(Role.ROLE_ADMIN.getValue())
	                .requestMatchers("/private/**").hasAnyRole(
	                		Role.ROLE_USER.getValue(),
	                		Role.ROLE_ADMIN.getValue(),
	                		Role.ROLE_FSK.getValue()
	                		)           
	                .requestMatchers(PathRequest.toH2Console()).hasRole(Role.ROLE_ADMIN.getValue())
	                .anyRequest().authenticated()
	                )
	        .formLogin(Customizer.withDefaults())
	        .httpBasic(Customizer.withDefaults())
	        .sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
		 
			return security.build();
	}

}

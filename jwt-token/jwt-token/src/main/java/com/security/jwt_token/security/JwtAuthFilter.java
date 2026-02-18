package com.security.jwt_token.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.jwt_token.service.JwtService;
import com.security.jwt_token.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserService userService;

	public JwtAuthFilter(JwtService jwtService, UserService userService) {
		super();
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		String authHeader = request.getHeader("Authorization");
		String token=null;
		String username=null;
		
	    if(authHeader!=null && authHeader.startsWith("Bearer ")) {
	    	token = authHeader.substring(7);
	    	username = jwtService.extractUser(token);
	    }
		
	    if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
	    	UserDetails user = userService.loadUserByUsername(username);
	         	if(jwtService.validateToken(token, user)) {
	         		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,  user.getAuthorities());
	         	    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	         		SecurityContextHolder.getContext().setAuthentication(authToken);
	         	}
	    }
	    filterChain.doFilter(request,response); 	
	}
	
}

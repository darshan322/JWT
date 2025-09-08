package com.jwt.java.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public String login(@RequestBody AuthRequest request) {
		try {
			/*
			 * Authentication is an interface in Spring Security that represents the current
			 * authentication state of a user.
			 */
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			// ✅ Step 2: If authentication is successful, generate token
			if (authentication.isAuthenticated()) {
				return "Bearer " + jwtUtil.generateToken(request.getUsername());
			} else {
				throw new RuntimeException("Invalid username or password");
			}

		} catch (AuthenticationException e) {
			throw new RuntimeException("Invalid username or password");
		}
	}
}

class AuthRequest {
	private String username;
	private String password;

	// Getters & Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

package com.sonali.crm.service.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sonali.crm.entity.Role;
import com.sonali.crm.entity.User;
import com.sonali.crm.repository.UserRepository;
import com.sonali.crm.security.JwtUtil;
import com.sonali.crm.service.UserService;

@Service
public class UserServiceImpl implements UserService{


	private UserRepository repo;	
	private BCryptPasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public UserServiceImpl(UserRepository repo, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		super();
		this.repo = repo;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	
	@Override
	public void registerUser(String name, String email, String password) {
		
		Optional<User>  existingUser = repo.findByEmail(email);
		if(existingUser.isPresent()) {
			throw new RuntimeException("User already exists with this email");
		}
		
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(Role.EMPLOYEE);
		
		repo.save(user);
		
	}

	@Override
	public String loginUser(String email, String password) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(email, password)
				);
		
		if(authentication.isAuthenticated()) {
			return jwtUtil.generateToken(email);
		}
		
		
		throw new RuntimeException("Invalid login");
	}
	
	
	public long getTotalEmployees() {
		
		return repo.countByRole(Role.EMPLOYEE);
	}
	
}

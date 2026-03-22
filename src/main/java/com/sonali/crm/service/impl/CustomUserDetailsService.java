package com.sonali.crm.service.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sonali.crm.entity.User;
import com.sonali.crm.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository repo;
	
	public CustomUserDetailsService(UserRepository repo) {
		this.repo = repo;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> userOptional = repo.findByEmail(email);
		
		if(userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
		}
		
		User user = userOptional.get();
		return org.springframework.security.core.userdetails.User
				.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRole().name())
				.build();				
	}
	
}

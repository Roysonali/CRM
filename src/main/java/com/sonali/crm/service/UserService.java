package com.sonali.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sonali.crm.repository.UserRepository;

@Service
public interface UserService {
	void registerUser(String name, String email, String password);
	String loginUser(String email, String Password);
	long getTotalEmployees();
}

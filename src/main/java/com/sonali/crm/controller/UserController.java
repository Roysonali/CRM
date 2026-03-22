package com.sonali.crm.controller;




import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.sonali.crm.entity.User;
import com.sonali.crm.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/test")
	public String testApi() {
	    return "JWT is working!";
	}
	
	@PostMapping("/register")
	public String register(@RequestBody User user) {
		userService.registerUser(
				user.getName(),
				user.getEmail(),
				user.getPassword()
				);
		return "User registered successfully";
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		return userService.loginUser(
				user.getEmail(),
				user.getPassword());
		
	}
	
	@GetMapping("/admin/dashboard")
	public String adminApi() {
		return "Welcome Admin Ji";
	}
	
	@GetMapping("/employee/profile")
	public String employeeApi() {
		return "Welcome employee";
	}
	
	
	@GetMapping("/dashboard/total-employee")
	public long totalEmployees() {
		
		return userService.getTotalEmployees();
	}
	
}

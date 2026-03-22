package com.sonali.crm.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sonali.crm.dto.ChartDataDTO;
import com.sonali.crm.entity.Customer;
import com.sonali.crm.service.CustomerService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	@Autowired
	private  CustomerService service;
	
	@PostMapping("/create")
	public Customer createCustomer(@RequestBody Customer customer) {
		return service.createCustomer(customer);
	}
	
	
	@PutMapping("/assign")
	public Customer assignCustomer(@RequestParam Long customerId, @RequestParam Long userId) {
		
		return service.assignCustomer(customerId, userId);
	}
	
	@GetMapping("/my")
	public List<Customer> getMyCustomer(Authentication authentication){
			
			String email = authentication.getName(); // ye jwt se aaya hua email h
			
			return service.getMyCustomerByEmail(email);
		}
	

	@GetMapping("/all")
	 public Page<Customer> getAllCustomers( @RequestParam int page,  @RequestParam int size,  @RequestParam(defaultValue = "id") String sortBy){
		
		return service.getAllCustomers(page, size, sortBy);
	}
	
	
	@GetMapping("/search")
	public Page<Customer> searchCustomer(@RequestParam String name, @RequestParam int page, @RequestParam int size, @RequestParam(defaultValue ="id") String sortBy){
		
		return service.searchCustomers(name, page, size, sortBy);
	}
	
	@GetMapping("/dashboard/total-customers")
	public long totalCustomers() {
		return service.getTotalCustomers();
	}
	
	@GetMapping("/dashboard/customers-per-employee")
	public List<Object[]> customersPerEmployee(){
		
		return service.getCustomersPerEmployee();
	}
	
	@GetMapping("/dashboard/recent-customers")
	public List<Customer> getRecentCustomers(){
		
		return service.getRecentCustomers();
	}
	
	@GetMapping("/dashboard/chart")
	public ChartDataDTO getChartData() {
	    return service.getCustomerChartData();
	}
	

	@GetMapping("/export")
	public void exportCustomers(HttpServletResponse response) throws IOException {
	    service.exportCustomers(response);
	}
}

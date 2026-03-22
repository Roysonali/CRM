package com.sonali.crm.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sonali.crm.dto.ChartDataDTO;
import com.sonali.crm.entity.Customer;

import jakarta.servlet.http.HttpServletResponse;


public interface CustomerService {
	Customer createCustomer(Customer customer);
	
	Customer assignCustomer(Long customerId, Long userId);
	
	List<Customer> getMyCustomerByEmail(String email);
	
	List<Customer> getAllCustomers();
	
	Page<Customer> getAllCustomers(int page, int size, String sortBy);
	
	Page<Customer> searchCustomers(String name, int page, int size, String sortBy);
	
	long getTotalCustomers();
	List<Object[]> getCustomersPerEmployee();
	
	List<Customer> getRecentCustomers();
	
	ChartDataDTO getCustomerChartData();
	void exportCustomers(HttpServletResponse response) throws IOException;
}

package com.sonali.crm.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sonali.crm.dto.ChartDataDTO;
import com.sonali.crm.entity.Customer;
import com.sonali.crm.entity.User;
import com.sonali.crm.repository.CustomerRepository;
import com.sonali.crm.repository.UserRepository;
import com.sonali.crm.service.CustomerService;
import com.sonali.crm.service.EmailService;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private   CustomerRepository repo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private  EmailService emailService;
	
	@Override
	public Customer createCustomer(Customer customer) {
		
		return repo.save(customer);
	}

	@Override
	public Customer assignCustomer(Long customerId, Long userId) {
		
		Customer customer = repo.findById(customerId)
		.orElseThrow(() -> new RuntimeException("Customer not found"));
		
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		customer.setAssignedTo(user);
		
		Customer saved = repo.save(customer);
		
		String body = "Hello " + user.getName() + ",\n\n"
		        + "You have been assigned a new customer: " + customer.getName() + "\n\n"
		        + "Thanks,\nCRM Team";
		
		emailService.sendEmail(
				user.getEmail(),
				"New Customer Assigned",
				body
				);
		return saved;
	}

	@Override
	public List<Customer> getMyCustomerByEmail(String email) {
		
		//Email -> user (nikal rhe h)
		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		//ab us user k assigned customers ko lao
		
		return repo.findByAssignedToId(user.getId());
	}

	@Override
	public List<Customer> getAllCustomers() {
		
		return repo.findAll();
	}

	@Override
	public Page<Customer> getAllCustomers(int page, int size, String sortBy) {
		
		return repo.findAll(PageRequest.of(page, size,Sort.by(sortBy)));
	}

	@Override
	public Page<Customer> searchCustomers(String name, int page, int size, String sortBy) {
		
		return repo.findByNameContainingIgnoreCase(name, PageRequest.of(page, size,Sort.by(sortBy)));
	}

	@Override
	public long getTotalCustomers() {
		
		return repo.count();
	}

	@Override
	public List<Object[]> getCustomersPerEmployee() {
		
		return repo.countCustomersPerEmployee();
	}

	@Override
	public List<Customer> getRecentCustomers() {
		
		return repo.findTop5ByOrderByCreatedAtDesc();
	}

	@Override
	public ChartDataDTO getCustomerChartData() {
		
		List<Object[]> result = repo.countCustomersPerEmployee();
		
		List<String> labels = new ArrayList<>();
		List<Long> data = new ArrayList<Long>();
		
		for(Object[] row : result) {
			labels.add((String) row[0]);
			data.add((Long) row[1]);
		}
		
		return new ChartDataDTO(labels, data);
	}

	@Override
	public void exportCustomers(HttpServletResponse response) throws IOException {
	
		 List<Customer> customers = repo.findAll();

		    Workbook workbook = new XSSFWorkbook();
		    Sheet sheet = workbook.createSheet("Customers");

		    // Header row
		    Row headerRow = sheet.createRow(0);
		    headerRow.createCell(0).setCellValue("ID");
		    headerRow.createCell(1).setCellValue("Name");
		    headerRow.createCell(2).setCellValue("Email");
		    headerRow.createCell(3).setCellValue("Phone");
		    headerRow.createCell(4).setCellValue("Company");

		    // Data rows
		    int rowNum = 1;

		    for(Customer c : customers) {
		        Row row = sheet.createRow(rowNum++);
		        row.createCell(0).setCellValue(c.getId());
		        row.createCell(1).setCellValue(c.getName());
		        row.createCell(2).setCellValue(c.getEmail());
		        row.createCell(3).setCellValue(c.getPhone());
		        row.createCell(4).setCellValue(c.getCompany());
		    }

		    // Response setup
		    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		    response.setHeader("Content-Disposition", "attachment; filename=customers.xlsx");

		    workbook.write(response.getOutputStream());
		    workbook.close();
	}

}

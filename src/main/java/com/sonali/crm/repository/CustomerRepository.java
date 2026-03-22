package com.sonali.crm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sonali.crm.entity.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	List<Customer> findByAssignedToId(Long userId);
	List<Customer> findAll();
	Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query("SELECT c.assignedTo.name, COUNT(c) FROM Customer c GROUP BY c.assignedTo.name")
	List<Object[]> countCustomersPerEmployee();
	
	List<Customer> findTop5ByOrderByCreatedAtDesc();
}

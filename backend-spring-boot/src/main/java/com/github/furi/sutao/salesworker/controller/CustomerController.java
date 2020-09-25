package com.github.furi.sutao.salesworker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.furi.sutao.salesworker.exception.ResourceNotFoundException;
import com.github.furi.sutao.salesworker.model.Customer;
import com.github.furi.sutao.salesworker.repository.CustomerRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	// get all customers
	@GetMapping("/customers")
	public List<Customer> getAllCustomer(){
		return customerRepository.findAll();
	}		
	
	// create customer rest api
	@PostMapping("/customers")
	public Customer createEmployee(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}
	
	// get customer by cd rest api
	@GetMapping("/customers/{cd}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long cd) {
		Customer customer = customerRepository.findById(cd)
				.orElseThrow(() -> new ResourceNotFoundException("Customers not exist with cd :" + cd));
		return ResponseEntity.ok(customer);
	}
	
	// update customer rest api
	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateEmployee(@PathVariable Long cd, @RequestBody Customer employeeDetails){
		Customer customer = customerRepository.findById(cd)
				.orElseThrow(() -> new ResourceNotFoundException("Customers not exist with cd :" + cd));
		
				customer.setName(employeeDetails.getName());

				Customer updatedEmployee = customerRepository.save(customer);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	// delete customer rest api
	@DeleteMapping("/customers/{cd}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long cd){
		Customer customer = customerRepository.findById(cd)
				.orElseThrow(() -> new ResourceNotFoundException("Customers not exist with cd :" + cd));
		
				customerRepository.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
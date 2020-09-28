package com.github.furi.sutao.salesworker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.furi.sutao.salesworker.exception.ResourceNotFoundException;
import com.github.furi.sutao.salesworker.model.Customer;
import com.github.furi.sutao.salesworker.repository.CustomerRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1/")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
    
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

	// get all customers
	@GetMapping("/customers")
	public List<Customer> getAllCustomer(){
		return customerRepository.findAll();
    }
    
    @GetMapping("/sorted-customers")
    public ResponseEntity<List<Customer>> getSortedCustomers(
            @RequestParam(defaultValue = "cd,desc") String[] sort) {

        try {
            List<Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Customer> customers = customerRepository.findAll(Sort.by(orders));

            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customers/name")
    public ResponseEntity<Map<String, Object>> getAllCustomersPage(
            @RequestParam(required = false) String name, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(defaultValue = "cd,desc") String[] sort) {

        try {
            List<Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Customer> customers = new ArrayList<>();
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Customer> pageTuts;
            if (name == null)
                pageTuts = customerRepository.findAll(pagingSort);
            else
                pageTuts = customerRepository.findByNameContaining(name, pagingSort);

                customers = pageTuts.getContent();

            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("customers", customers);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
	@PutMapping("/customers/{cd}")
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
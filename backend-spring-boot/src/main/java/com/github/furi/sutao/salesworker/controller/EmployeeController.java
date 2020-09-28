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
import com.github.furi.sutao.salesworker.model.Employee;
import com.github.furi.sutao.salesworker.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
    
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

	// get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
    }
    
    @GetMapping("/sorted-employees")
    public ResponseEntity<List<Employee>> getSortedEmployees(
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

            List<Employee> employees = employeeRepository.findAll(Sort.by(orders));

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employees/name")
    public ResponseEntity<Map<String, Object>> getAllEmployeesPage(
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

            List<Employee> employees = new ArrayList<>();
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Employee> pageTuts;
            if (name == null)
                pageTuts = employeeRepository.findAll(pagingSort);
            else
                pageTuts = employeeRepository.findByNameContaining(name, pagingSort);

                employees = pageTuts.getContent();

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("employees", employees);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	// create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	// get employee by cd rest api
	@GetMapping("/employees/{cd}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long cd) {
		Employee employee = employeeRepository.findById(cd)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with cd :" + cd));
		return ResponseEntity.ok(employee);
	}
	
	// update employee rest api
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long cd, @RequestBody Employee employeeDetails){
		Employee employee = employeeRepository.findById(cd)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with cd :" + cd));
		
		employee.setName(employeeDetails.getName());

		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	// delete employee rest api
	@DeleteMapping("/employees/{cd}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long cd){
		Employee employee = employeeRepository.findById(cd)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with cd :" + cd));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
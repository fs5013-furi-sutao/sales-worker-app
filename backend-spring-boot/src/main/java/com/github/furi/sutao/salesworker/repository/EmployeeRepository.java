package com.github.furi.sutao.salesworker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.furi.sutao.salesworker.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}

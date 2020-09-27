package com.github.furi.sutao.salesworker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.github.furi.sutao.salesworker.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    Page<Employee> findByNameContaining(String name, Pageable pageable);
    List<Employee> findByNameContaining(String name, Sort sort);
}

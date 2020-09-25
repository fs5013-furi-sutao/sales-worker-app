package com.github.furi.sutao.salesworker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.furi.sutao.salesworker.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}

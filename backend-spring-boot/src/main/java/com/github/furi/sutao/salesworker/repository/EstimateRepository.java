package com.github.furi.sutao.salesworker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.furi.sutao.salesworker.model.Estimate;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long>{

}

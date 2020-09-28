package com.github.furi.sutao.salesworker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.github.furi.sutao.salesworker.model.Estimate;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    @Query(value = "SELECT e.id, e.name, e.amount, e.budgeted_amount, e.customer_cd, e.employee_cd, e.date, e.status, e.order_id FROM estimates e WHERE e.id REGEXP :id ORDER BY e.id ASC",
            nativeQuery = true)
    List<Estimate> getEstimatesByLikeId(String id);

    @Query(value = "SELECT e.id, e.name, e.amount, e.budgeted_amount, e.customer_cd, e.employee_cd, e.date, e.status, e.order_id FROM estimates e WHERE e.name REGEXP :name ORDER BY e.id ASC",
            nativeQuery = true)
    List<Estimate> getEstimatesByLikeName(String name);

    @Query(value = "SELECT e.id, e.name, e.amount, e.budgeted_amount, e.customer_cd, e.employee_cd, e.date, e.status, e.order_id FROM estimates e WHERE e.status REGEXP :status ORDER BY e.id ASC",
            nativeQuery = true)
    List<Estimate> getEstimatesByLikeStatus(String status);

    @Query(value = "SELECT e.id, e.name, e.amount, e.budgeted_amount, e.customer_cd, e.employee_cd, e.date, e.status, e.order_id FROM estimates e WHERE e.customer_cd = :cd ORDER BY e.id ASC",
            nativeQuery = true)
    List<Estimate> getEstimatesByCustomerCd(String cd);

    @Query(value = "SELECT e.id, e.name, e.amount, e.budgeted_amount, e.customer_cd, e.employee_cd, e.date, e.status, e.order_id FROM estimates e WHERE e.employee_cd = :cd ORDER BY e.id ASC",
            nativeQuery = true)
    List<Estimate> getEstimatesByEmployeeCd(String cd);
}

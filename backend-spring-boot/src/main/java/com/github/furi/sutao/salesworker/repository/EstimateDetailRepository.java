package com.github.furi.sutao.salesworker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.github.furi.sutao.salesworker.model.EstimateDetail;

@Repository
public interface EstimateDetailRepository extends JpaRepository<EstimateDetail, Long> {
    @Query(value = "SELECT ed.id, ed.sub_id, ed.estimate_id, ed.product_cd, ed.quantity FROM estimate_details ed WHERE ed.estimate_id = :estimateId ORDER BY ed.sub_id ASC",
            nativeQuery = true)
    List<EstimateDetail> getEstimateDetailsByEstimateId(long estimateId);
}

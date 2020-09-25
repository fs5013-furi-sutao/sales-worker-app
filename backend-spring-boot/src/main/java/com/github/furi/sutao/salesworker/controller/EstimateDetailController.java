package com.github.furi.sutao.salesworker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.furi.sutao.salesworker.exception.ResourceNotFoundException;
import com.github.furi.sutao.salesworker.model.EstimateDetail;
import com.github.furi.sutao.salesworker.repository.EstimateDetailRepository;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EstimateDetailController {

    @Autowired
    private EstimateDetailRepository estimateDetailRepository;

    // get all estimate details
    @GetMapping("/estimate-details")
    public List<EstimateDetail> getAllEstimates() {
        return estimateDetailRepository.findAll();
    }

    // create estimate detail rest api
    @PostMapping("/estimate-details")
    public EstimateDetail createEstimate(@RequestBody EstimateDetail estimateDetail) {
        return estimateDetailRepository.save(estimateDetail);
    }

    // get estimate detail by id rest api
    @GetMapping("/estimate-details/{id}")
    public ResponseEntity<EstimateDetail> getEstimateById(@PathVariable Long id) {
        EstimateDetail estimateDetail = estimateDetailRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("EstimateDetail not exist with id :" + id));
        return ResponseEntity.ok(estimateDetail);
    }

    // update estimate detail rest api

    @PutMapping("/estimate-details/{id}")
    public ResponseEntity<EstimateDetail> updateEstimate(@PathVariable Long id,
            @RequestBody EstimateDetail estimateDetails) {
        EstimateDetail estimateDetail = estimateDetailRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("EstimateDetail not exist with id :" + id));

        estimateDetail.setSubId(estimateDetails.getSubId());
        estimateDetail.setEstimateId(estimateDetails.getEstimateId());
        estimateDetail.setProductCd(estimateDetails.getProductCd());
        estimateDetail.setQuantity(estimateDetails.getQuantity());

        EstimateDetail updatedEstimateDetail = estimateDetailRepository.save(estimateDetail);
        return ResponseEntity.ok(updatedEstimateDetail);
    }

    // delete estimate detail rest api
    @DeleteMapping("/estimate-details/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEstimate(@PathVariable Long id) {
        EstimateDetail estimateDetail = estimateDetailRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("EstimateDetail not exist with id :" + id));

        estimateDetailRepository.delete(estimateDetail);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}

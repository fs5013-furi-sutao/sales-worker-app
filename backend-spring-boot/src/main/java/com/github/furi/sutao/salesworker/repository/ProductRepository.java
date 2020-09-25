package com.github.furi.sutao.salesworker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.github.furi.sutao.salesworker.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p.cd, p.name, p.price FROM mst_products p ORDER BY p.cd DESC LIMIT 1",
            nativeQuery = true)
    Product getMaxCdProducts();

    Page<Product> findByNameContaining(String name, Pageable pageable);
    List<Product> findByNameContaining(String name, Sort sort);
}

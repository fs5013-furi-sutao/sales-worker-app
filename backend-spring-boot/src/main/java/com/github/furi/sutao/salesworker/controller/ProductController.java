package com.github.furi.sutao.salesworker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.furi.sutao.salesworker.exception.ResourceNotFoundException;
import com.github.furi.sutao.salesworker.model.Product;
import com.github.furi.sutao.salesworker.repository.ProductRepository;
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

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1/")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    // get all products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/sorted-products")
    public ResponseEntity<List<Product>> getSortedProducts(
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

            List<Product> products = productRepository.findAll(Sort.by(orders));

            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/name")
    public ResponseEntity<Map<String, Object>> getAllProductsPage(
            @RequestParam(required = false) String name, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
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

            List<Product> products = new ArrayList<>();
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Product> pageTuts;
            if (name == null)
                pageTuts = productRepository.findAll(pagingSort);
            else
                pageTuts = productRepository.findByNameContaining(name, pagingSort);

            products = pageTuts.getContent();

            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("products", products);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get product of max cd
    @GetMapping("/products/maxcd")
    public Product getMaxCdProducts() {
        return productRepository.getMaxCdProducts();
    }

    // create product rest api
    @PostMapping("/products")
    public Product createEstimate(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // get product by id rest api
    @GetMapping("/products/{cd}")
    public ResponseEntity<Product> getEstimateById(@PathVariable Long cd) {
        Product product = productRepository.findById(cd).orElseThrow(
                () -> new ResourceNotFoundException("Product not exist with cd :" + cd));
        return ResponseEntity.ok(product);
    }

    // update product rest api
    @PutMapping("/products/{cd}")
    public ResponseEntity<Product> updateEstimate(@PathVariable Long cd,
            @RequestBody Product productDetails) {
        Product product = productRepository.findById(cd).orElseThrow(
                () -> new ResourceNotFoundException("Product not exist with cd :" + cd));

        product.setCd(productDetails.getCd());
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    // delete product rest api
    @DeleteMapping("/products/{cd}")
    public ResponseEntity<Map<String, Boolean>> deleteEstimate(@PathVariable Long cd) {
        Product product = productRepository.findById(cd).orElseThrow(
                () -> new ResourceNotFoundException("Product not exist with id :" + cd));

        productRepository.delete(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

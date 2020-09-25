package com.github.furi.sutao.salesworker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mst_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd")
    private long cd;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private long price;

    public Product() {
	}

    public Product(String name, long price) {
            
		super();
		this.name = name;
		this.price = price;
	}

    public long getCd() {
        return this.cd;
    }

    public void setCd(long cd) {
        this.cd = cd;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return this.price;
    }

    public void setPrice(long price) {
        this.price = price;
    }  
}

package com.github.furi.sutao.salesworker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estimate_details")
public class EstimateDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "sub_id")
    private String subId;

    @Column(name = "estimate_id")
    private long estimateId;

    @Column(name = "product_cd")
    private String productCd;

    @Column(name = "quantity")
    private long quantity; 

    public EstimateDetail() {
	}

    public EstimateDetail(String subId, long estimateId, String productCd, long quantity) {
		super();
		this.subId = subId;
		this.estimateId = estimateId;
        this.productCd = productCd;
        this.quantity = quantity;
	}

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubId() {
        return this.subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public long getEstimateId() {
        return this.estimateId;
    }

    public void setEstimateId(long estimateId) {
        this.estimateId = estimateId;
    }

    public String getProductCd() {
        return this.productCd;
    }

    public void setProductCd(String productCd) {
        this.productCd = productCd;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }  
}

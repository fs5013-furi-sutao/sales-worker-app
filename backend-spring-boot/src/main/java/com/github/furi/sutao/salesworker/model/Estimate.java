package com.github.furi.sutao.salesworker.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estimates")
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "estimate_name")
    private String estimateName;

    @Column(name = "estimate_amount")
    private long estimateAmount;

    @Column(name = "budgeted_amount")
    private long budgetedAmount;

    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(name = "estimate_date")
    private Date estimateDate;

    @Column(name = "status")
    private String status;

    @Column(name = "order_id")
    private long orderId;

    public Estimate() {
	}

    public Estimate(String estimateName, long estimateAmount, long budgetedAmount, 
        String customerCode, Date estimateDate, String status, long orderId) {
            
		super();
		this.estimateName = estimateName;
		this.estimateAmount = estimateAmount;
        this.budgetedAmount = budgetedAmount;
        this.customerCode = customerCode;
		this.estimateDate = estimateDate;
        this.status = status;
        this.orderId = orderId;
	}

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEstimateName() {
        return this.estimateName;
    }

    public void setEstimateName(String estimateName) {
        this.estimateName = estimateName;
    }

    public long getEstimateAmount() {
        return this.estimateAmount;
    }

    public void setEstimateAmount(long estimateAmount) {
        this.estimateAmount = estimateAmount;
    }

    public long getBudgetedAmount() {
        return this.budgetedAmount;
    }

    public void setBudgetedAmount(long budgetedAmount) {
        this.budgetedAmount = budgetedAmount;
    }

    public String getCustomerCode() {
        return this.customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getEmployeeCode() {
        return this.employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public Date getEstimateDate() {
        return this.estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }   
}

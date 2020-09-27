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

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private long amount;

    @Column(name = "budgeted_amount")
    private long budgetedAmount;

    @Column(name = "customer_cd")
    private String customerCd;

    @Column(name = "employee_cd")
    private String employeeCd;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private String status;

    @Column(name = "order_id")
    private long orderId;

    public Estimate() {
	}

    public Estimate(String name, long amount, long budgetedAmount, 
        String customerCode, String employeeCd, Date date, String status, long orderId) {
            
		super();
		this.name = name;
		this.amount = amount;
        this.budgetedAmount = budgetedAmount;
        this.customerCd = customerCd;
        this.employeeCd = employeeCd;
		this.date = date;
        this.status = status;
        this.orderId = orderId;
	}

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getBudgetedAmount() {
        return this.budgetedAmount;
    }

    public void setBudgetedAmount(long budgetedAmount) {
        this.budgetedAmount = budgetedAmount;
    }

    public String getCustomerCd() {
        return this.customerCd;
    }

    public void setCustomerCd(String customerCd) {
        this.customerCd = customerCd;
    }

    public String getEmployeeCd() {
        return this.employeeCd;
    }

    public void setEmployeeCd(String employeeCd) {
        this.employeeCd = employeeCd;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
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

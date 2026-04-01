package com.finance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LoanApplication implements Serializable {
    private static final long serialVersionUID = 1L;

    private String applicationId;
    private String customerId;
    private BigDecimal amount;
    private String statusCode;
    private String address;
    private Date appliedAt;

    public LoanApplication() {}

    public LoanApplication(String applicationId, String customerId, BigDecimal amount) {
        this.applicationId = applicationId;
        this.customerId = customerId;
        this.amount = amount;
        this.statusCode = "PENDING";
    }

    public String getApplicationInfo() {
        return String.format("ID: %s, Amount: %s", applicationId, amount);
    }

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Date getAppliedAt() { return appliedAt; }
    public void setAppliedAt(Date appliedAt) { this.appliedAt = appliedAt; }
}
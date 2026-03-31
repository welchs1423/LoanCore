package com.finance.domain;

import java.math.BigDecimal;

public class LoanApplication {
    private String applicationId;
    private String customerId;
    private BigDecimal amount;
    private String statusCode;

    public LoanApplication(String applicationId, String customerId, BigDecimal amount) {
        this.applicationId = applicationId;
        this.customerId = customerId;
        this.amount = amount;
        this.statusCode = "PENDING";
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
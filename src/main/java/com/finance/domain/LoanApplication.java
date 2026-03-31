package com.finance.domain;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoanApplication {
    
    private String applicationId;

    @NotBlank(message = "고객 ID는 필수 입력값입니다.")
    private String customerId;

    @NotNull(message = "신청 금액을 입력해주세요.")
    @Min(value = 10000, message = "대출 신청 금액은 최소 10,000원 이상이어야 합니다.")
    private BigDecimal amount;

    private String statusCode;

    public LoanApplication() {
        this.statusCode = "PENDING";
    }

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

    public String getApplicationInfo() {
        return "Application ID: " + applicationId + ", Customer ID: " + customerId + ", Amount: " + amount + ", Status: " + statusCode;
    }
}
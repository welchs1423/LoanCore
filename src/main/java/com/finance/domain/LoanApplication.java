package com.finance.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoanApplication {
	private String applicationId;

    @NotBlank(message = "고객 ID는 필수 입력값입니다.")
    @Size(min = 4, max = 20, message = "고객 ID는 4자 이상 20자 이하로 입력해야 합니다.")
    private String customerId;

    @NotNull(message = "신청 금액을 입력해주세요.")
    @Min(value = 10000, message = "대출 신청 금액은 최소 10,000원 이상이어야 합니다.")
    private BigDecimal amount;

    private String statusCode;
    private String fileName;

    @NotBlank(message = "주소는 필수 입력값입니다.")
    private String address;

    private String delYn;
    private Timestamp appliedAt;

    public LoanApplication() {
        this.statusCode = "PENDING";
        this.delYn = "N";
    }

    public LoanApplication(String applicationId, String customerId, BigDecimal amount) {
        this.applicationId = applicationId;
        this.customerId = customerId;
        this.amount = amount;
        this.statusCode = "PENDING";
        this.delYn = "N";
    }

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDelYn() { return delYn; }
    public void setDelYn(String delYn) { this.delYn = delYn; }
    public Timestamp getAppliedAt() { return appliedAt; }
    public void setAppliedAt(Timestamp appliedAt) { this.appliedAt = appliedAt; }

    public String getApplicationInfo() {
        return "Application ID: " + applicationId + ", Customer ID: " + customerId + ", Amount: " + amount + ", Status: " + statusCode;
    }
}
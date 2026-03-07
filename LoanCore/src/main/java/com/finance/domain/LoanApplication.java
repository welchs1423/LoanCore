package com.finance.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanApplication {
	private String applicationId;
	private String customerId;
	private BigDecimal applyAmount;
	private String statusCode;
	private LocalDateTime applyDate;
	
	public LoanApplication(String applicationId, String customerId, BigDecimal applyAmount) {
		this.applicationId = applicationId;
		this.customerId = customerId;
		this.applyAmount = applyAmount;
		this.statusCode = "APPLY";
		this.applyDate = LocalDateTime.now();
	}
	
	public void changeStatus(String newStatusCode) {
		this.statusCode = newStatusCode;
	}
	
	public String getApplicationInfo() {
	    return String.format("[대출신청] 번호: %s, 고객: %s, 금액: %s원, 상태: %s, 일시: %s", 
	            applicationId, customerId, applyAmount, statusCode, applyDate);
	}
	
	public BigDecimal getApplyAmount() {
        return this.applyAmount;
    }
	
}

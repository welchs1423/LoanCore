package com.finance.service;

import com.finance.domain.LoanApplication;

public class LoanReviewService {
	
	public String reviewLoan(LoanApplication application) {
		java.math.BigDecimal limit = new java.math.BigDecimal("50000000");
		
		if (application.getApplyAmount().compareTo(limit) >= 0) {
			application.changeStatus("REJECT");
			return "심사 거절: 신청 금액이 한도를 초과했습니다.";
		} else {
			application.changeStatus("APPROVE");
			return "심사 승인: 기표 대기 상태로 변경되었습니다.";
		}
	}
}

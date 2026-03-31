package com.finance.service;

import com.finance.domain.LoanApplication;
import java.util.ArrayList;
import java.util.List;

public class LoanReviewService {

    // 임시 메모리 저장소
    private List<LoanApplication> repository = new ArrayList<>();

    public String reviewLoan(LoanApplication app) {
        String resultMessage;
        
        if (app.getAmount().compareTo(new java.math.BigDecimal("100000000")) > 0) {
            app.setStatusCode("REJECT");
            resultMessage = "거절: 1억원 초과 대출은 지점 방문이 필요합니다.";
        } else {
            app.setStatusCode("APPROVE");
            resultMessage = "승인: 기표 대기 상태로 변경되었습니다.";
        }
        
        // 심사 완료 후 리스트에 저장
        repository.add(app);
        
        return resultMessage;
    }

    public List<LoanApplication> getAllApplications() {
        return repository;
    }
}
package com.finance.service;

import com.finance.domain.LoanApplication;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanReviewService {

    private List<LoanApplication> repository = new ArrayList<>();

    public String reviewLoan(LoanApplication app) {
        String resultMessage;
        
        if (app.getAmount().compareTo(new BigDecimal("100000000")) > 0) {
            app.setStatusCode("REJECT");
            resultMessage = "거절: 1억원 초과 대출은 지점 방문이 필요합니다.";
        } else {
            app.setStatusCode("APPROVE");
            resultMessage = "승인: 기표 대기 상태로 변경되었습니다.";
        }
        
        repository.add(app);
        
        return resultMessage;
    }

    public List<LoanApplication> getAllApplications() {
        return repository;
    }

    public LoanApplication getApplicationById(String id) {
        for (LoanApplication app : repository) {
            if (app.getApplicationId().equals(id)) {
                return app;
            }
        }
        return null;
    }

    public void deleteApplication(String id) {
        repository.removeIf(app -> app.getApplicationId().equals(id));
    }

    public void updateApplication(String id, String customerId, BigDecimal amount) {
        LoanApplication app = getApplicationById(id);
        if (app != null) {
            app.setCustomerId(customerId);
            app.setAmount(amount);
            
            if (amount.compareTo(new BigDecimal("100000000")) > 0) {
                app.setStatusCode("REJECT");
            } else {
                app.setStatusCode("APPROVE");
            }
        }
    }

    public List<LoanApplication> searchApplications(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return repository;
        }
        return repository.stream()
                .filter(app -> app.getCustomerId().contains(keyword))
                .collect(Collectors.toList());
    }
}
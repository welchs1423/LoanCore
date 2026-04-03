package com.finance.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class PaymentService {

    private final LoanMapper loanMapper;

    public PaymentService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void processPayment(String applicationId, BigDecimal amount) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        if (app != null && "APPROVED".equals(app.getStatusCode())) {
            
            BigDecimal currentAmount = app.getAmount();
            BigDecimal remainingAmount = currentAmount.subtract(amount);
            
            if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
                app.setStatusCode("COMPLETED");
                app.setAmount(BigDecimal.ZERO);
            } else {
                app.setAmount(remainingAmount);
            }
            
            loanMapper.updateApplication(app);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void processOverdueLoans() {
        List<LoanApplication> activeLoans = loanMapper.searchApplicationsDynamic(null, "APPROVED", null, null);
        
        for (LoanApplication loan : activeLoans) {
            loan.setStatusCode("OVERDUE");
            loanMapper.updateApplication(loan);
        }
    }
}
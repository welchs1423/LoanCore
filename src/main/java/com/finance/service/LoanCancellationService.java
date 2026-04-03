package com.finance.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class LoanCancellationService {

    private final LoanMapper loanMapper;

    public LoanCancellationService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelLoan(String applicationId) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        
        if (app != null) {
            // 청약 철회 상태로 변경 및 남은 대출 금액 초기화
            app.setStatusCode("CANCELED");
            app.setAmount(BigDecimal.ZERO);
            
            loanMapper.updateApplication(app);
        }
    }
}
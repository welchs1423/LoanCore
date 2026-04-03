package com.finance.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class LoanLiquidationService {

    private final LoanMapper loanMapper;

    public LoanLiquidationService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public String closeLoan(String applicationId) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        if (app != null && app.getAmount().doubleValue() <= 0) {
            app.setStatusCode("CLOSED");
            loanMapper.updateApplication(app);
            return UUID.randomUUID().toString();
        }
        return null;
    }
}
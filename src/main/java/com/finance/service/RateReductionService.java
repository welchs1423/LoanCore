package com.finance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class RateReductionService {

    private final LoanMapper loanMapper;

    public RateReductionService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean processReductionRequest(String applicationId, int previousScore, int currentScore) {
        if (currentScore - previousScore >= 50) {
            LoanApplication app = loanMapper.selectApplicationById(applicationId);
            if (app != null && "ACTIVE".equals(app.getStatusCode())) {
                app.setStatusCode("RATE_REDUCED");
                loanMapper.updateApplication(app);
                return true;
            }
        }
        return false;
    }
}
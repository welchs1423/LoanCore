package com.finance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class RolloverService {

    private final LoanMapper loanMapper;
    private final InterestRateService interestRateService;

    public RolloverService(LoanMapper loanMapper, InterestRateService interestRateService) {
        this.loanMapper = loanMapper;
        this.interestRateService = interestRateService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void processRollover(String applicationId, int currentCreditScore) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        
        if (app != null && "APPROVED".equals(app.getStatusCode())) {
            double newRate = interestRateService.calculateApplicableRate(currentCreditScore);
            
            app.setStatusCode("ROLLOVER");
            System.out.println("연장 심사 완료. 갱신 금리: " + newRate + "%");
            
            loanMapper.updateApplication(app);
        }
    }
}
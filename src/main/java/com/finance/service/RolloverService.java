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
            
            // 만기 연장 상태로 변경 및 이력 갱신 로직
            app.setStatusCode("ROLLOVER");
            // 실제 환경에서는 만기일(dueDate)을 +1년 등으로 연장하는 로직이 추가됩니다.
            
            loanMapper.updateApplication(app);
        }
    }
}
package com.finance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class LoanDisbursementService {

    private final LoanMapper loanMapper;

    public LoanDisbursementService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void disburseLoan(String applicationId, String bankCode, String accountNumber) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        if (app != null && "APPROVED".equals(app.getStatusCode())) {
            app.setStatusCode("ACTIVE");
            loanMapper.updateApplication(app);
            System.out.println("송금처리은행코드: " + bankCode + " 계좌번호: " + accountNumber + " 금액: " + app.getAmount());
        }
    }
}
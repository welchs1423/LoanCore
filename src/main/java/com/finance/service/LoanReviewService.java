package com.finance.service;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class LoanReviewService {

    private final LoanMapper loanMapper;
    private final CreditEvaluationService creditService;
    private final RepaymentService repaymentService;
    private final EmailNotificationService emailService;

    public LoanReviewService(LoanMapper loanMapper, 
                             CreditEvaluationService creditService, 
                             RepaymentService repaymentService, 
                             EmailNotificationService emailService) {
        this.loanMapper = loanMapper;
        this.creditService = creditService;
        this.repaymentService = repaymentService;
        this.emailService = emailService;
    }

    @Transactional
    public void applyLoan(LoanApplication app) {
        int creditScore = creditService.evaluateCreditScore(app.getCustomerId());
        
        if (creditService.isApproved(creditScore)) {
            app.setStatusCode("PENDING");
        } else {
            app.setStatusCode("REJECTED");
        }
        
        loanMapper.insertApplication(app);
    }

    @Transactional
    public void approveLoan(String applicationId, String emailAddress, File approvalDocument) {
        loanMapper.updateStatus(applicationId, "APPROVED");
        
        try {
            emailService.sendApprovalEmail(emailAddress, approvalDocument);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Map<String, Object>> generateSchedule(String applicationId) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        return repaymentService.calculateSchedule(app.getAmount(), 5.0, 36);
    }
}
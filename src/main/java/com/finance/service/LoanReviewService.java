package com.finance.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.domain.AuditLog;
import com.finance.domain.LoanApplication;
import com.finance.domain.LoanMemo;
import com.finance.mapper.AuditLogMapper;
import com.finance.mapper.LoanMapper;
import com.finance.mapper.LoanMemoMapper;

@Service
public class LoanReviewService {

    @Autowired
    private LoanMapper loanMapper;
    @Autowired
    private LoanMemoMapper loanMemoMapper;
    @Autowired
    private AuditLogMapper auditLogMapper;
    
    @Autowired(required = false)
    private CreditEvaluationService creditService;
    @Autowired(required = false)
    private RepaymentService repaymentService;
    @Autowired(required = false)
    private EmailNotificationService emailService;

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "loanCache", allEntries = true) 
    public void applyLoan(LoanApplication app) {
        if (app.getApplicationId() == null || app.getApplicationId().isEmpty()) {
            app.setApplicationId("L" + System.currentTimeMillis());
        }
        
        if (creditService != null) {
            int creditScore = creditService.evaluateCreditScore(app.getCustomerId());
            if (creditService.isApproved(creditScore)) {
                app.setStatusCode("PENDING");
            } else {
                app.setStatusCode("REJECTED");
            }
        }
        
        loanMapper.insertApplication(app);
    }

    public String reviewLoan(LoanApplication app) {
        app.setStatusCode("APPROVE");
        return app.getStatusCode();
    }

    @Cacheable(value = "loanCache", key = "'allLoans'")
    public List<LoanApplication> getAllApplications() {
        return loanMapper.selectAllApplications();
    }

    public LoanApplication getApplicationById(String id) {
        return loanMapper.selectApplicationById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void approveLoan(String id) {
        LoanApplication app = loanMapper.selectApplicationById(id);
        if (app != null) {
            app.setStatusCode("APPROVED");
            loanMapper.updateApplication(app);
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void approveLoan(String applicationId, String emailAddress, File approvalDocument) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        if (app != null) {
            app.setStatusCode("APPROVED");
            loanMapper.updateApplication(app);
        }
        
        if (emailService != null) {
            try {
                emailService.sendApprovalEmail(emailAddress, approvalDocument);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getTotalCount() {
        return loanMapper.countAllApplications();
    }

    public List<LoanMemo> getMemosByAppId(String appId) {
        return loanMemoMapper.selectMemosByApplicationId(appId);
    }

    public List<AuditLog> getRecentAuditLogs() {
        return auditLogMapper.getRecentLogs();
    }

    public List<LoanApplication> searchApplications(String keyword, String status, String startDate, String endDate) {
        return loanMapper.searchApplicationsDynamic(keyword, status, startDate, endDate);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addMemo(LoanMemo memo) {
        loanMemoMapper.insertMemo(memo);
    }

    public List<Map<String, Object>> getLoanStatistics() {
        return loanMapper.getLoanStatistics();
    }
    
    public List<Map<String, Object>> generateSchedule(String applicationId) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        if (app != null && repaymentService != null) {
            return repaymentService.calculateSchedule(app.getAmount(), 5.0, 36);
        }
        return null;
    }
}
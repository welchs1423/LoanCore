package com.finance.service;

import com.finance.domain.LoanApplication;
import com.finance.domain.LoanMemo;
import com.finance.domain.AuditLog;
import com.finance.mapper.LoanMapper;
import com.finance.mapper.LoanMemoMapper;
import com.finance.mapper.AuditLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoanReviewService {

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private LoanMemoMapper loanMemoMapper;

    @Autowired
    private AuditLogMapper auditLogMapper;

    @Transactional(rollbackFor = Exception.class)
    public void applyLoan(LoanApplication app) {
        if (app.getApplicationId() == null || app.getApplicationId().isEmpty()) {
            app.setApplicationId("L" + System.currentTimeMillis());
        }
        loanMapper.insertApplication(app);
    }

    // MainTest.java 오류 해결: void -> String 반환으로 변경
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

    public int getTotalCount() {
        return loanMapper.countAllApplications();
    }

    public List<LoanMemo> getMemosByAppId(String appId) {
        return loanMemoMapper.selectMemosByApplicationId(appId);
    }

    // 타입 불일치 오류 해결: List<Map> -> List<AuditLog> 로 변경
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
}
package com.finance.service;

import com.finance.domain.AuditLog;
import com.finance.domain.LoanApplication;
import com.finance.domain.LoanMemo;
import com.finance.mapper.AuditLogMapper;
import com.finance.mapper.LoanMapper;
import com.finance.mapper.LoanMemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoanReviewService {

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private AuditLogMapper auditLogMapper;

    @Autowired
    private LoanMemoMapper memoMapper;

    @Cacheable(value = "loanCache", key = "'totalCount'")
    public int getTotalCount() {
        return loanMapper.countAllApplications();
    }

    public List<LoanApplication> getAllApplications() {
        return loanMapper.selectAllApplications();
    }

    @CacheEvict(value = "loanCache", allEntries = true)
    public void applyLoan(LoanApplication app) {
        if (app.getApplicationId() == null || app.getApplicationId().isEmpty()) {
            app.setApplicationId("L" + System.currentTimeMillis());
        }
        loanMapper.insertApplication(app);
    }

    public LoanApplication getApplicationById(String id) {
        return loanMapper.selectApplicationById(id);
    }

    @CacheEvict(value = "loanCache", allEntries = true)
    public String reviewLoan(LoanApplication app) {
        app.setStatusCode("APPROVED");
        loanMapper.updateApplication(app);
        return app.getApplicationInfo();
    }

    public List<LoanApplication> searchApplications(String keyword, String status, String startDate, String endDate) {
        return loanMapper.searchApplicationsDynamic(keyword, status, startDate, endDate);
    }

    public List<LoanApplication> getApplicationsWithPaging(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return loanMapper.selectApplicationsWithPaging(offset, pageSize);
    }

    public List<AuditLog> getRecentAuditLogs() {
        return auditLogMapper.getRecentLogs();
    }

    public void addMemo(LoanMemo memo) {
        memoMapper.insertMemo(memo);
    }

    public List<LoanMemo> getMemosByAppId(String applicationId) {
        return memoMapper.selectMemosByAppId(applicationId);
    }
}
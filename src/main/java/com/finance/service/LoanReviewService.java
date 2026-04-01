package com.finance.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.annotation.LogExecutionTime;
import com.finance.domain.AuditLog;
import com.finance.domain.LoanApplication;
import com.finance.mapper.AuditLogMapper;
import com.finance.mapper.LoanMapper;

@Service
public class LoanReviewService {

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private NotificationService notificationService;

    @LogExecutionTime
    @Transactional
    @CacheEvict(value = "loanCache", allEntries = true)
    public String reviewLoan(LoanApplication app) {
        BigDecimal amount = app.getAmount();
        String resultMessage;

        if (amount.compareTo(new BigDecimal("100000000")) > 0) {
            app.setStatusCode("REJECT");
            resultMessage = "1억원을 초과하는 대출은 자동 거절됩니다.";
        } else if (amount.compareTo(new BigDecimal("50000000")) <= 0) {
            app.setStatusCode("APPROVE");
            resultMessage = "5천만원 이하 대출이 자동 승인되었습니다.";
        } else {
            app.setStatusCode("PENDING");
            resultMessage = "대출 심사가 접수되어 대기 중입니다.";
        }
        
        loanMapper.insertApplication(app);
        
        // MDC에서 traceId를 가져와 전달
        String traceId = MDC.get("traceId");
        notificationService.sendStatusChangeNotification(app.getCustomerId(), app.getStatusCode(), traceId);
        
        return resultMessage;
    }

    public List<LoanApplication> getAllApplications() {
        return loanMapper.selectAllApplications();
    }

    public LoanApplication getApplicationById(String id) {
        return loanMapper.selectApplicationById(id);
    }

    @LogExecutionTime
    public List<LoanApplication> searchApplications(String keyword, String status, String startDate, String endDate) {
        return loanMapper.searchApplicationsDynamic(keyword, status, startDate, endDate);
    }

    @Transactional
    @CacheEvict(value = "loanCache", allEntries = true)
    public void updateApplication(String id, String customerId, BigDecimal amount) {
        LoanApplication app = loanMapper.selectApplicationById(id);
        if (app != null) {
            app.setCustomerId(customerId);
            app.setAmount(amount);
            loanMapper.updateApplication(app);
        }
    }

    @Transactional
    @CacheEvict(value = "loanCache", allEntries = true)
    public void deleteApplication(String id) {
        loanMapper.deleteApplication(id);
    }

    @LogExecutionTime
    public List<LoanApplication> getApplicationsWithPaging(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return loanMapper.selectApplicationsWithPaging(offset, pageSize);
    }

    @Cacheable(value = "loanCache")
    public int getTotalCount() {
        return loanMapper.countAllApplications();
    }

    @LogExecutionTime
    @Transactional
    @CacheEvict(value = "loanCache", allEntries = true)
    public void updateStatusBulk(String status, List<String> ids) {
        loanMapper.updateApplicationStatusBulk(status, ids);
        
        // MDC에서 traceId를 가져와 전달
        String traceId = MDC.get("traceId");
        for (String id : ids) {
            LoanApplication app = loanMapper.selectApplicationById(id);
            if (app != null) {
                notificationService.sendStatusChangeNotification(app.getCustomerId(), status, traceId);
            }
        }
    }
    
    @Autowired
    private AuditLogMapper auditLogMapper;

    public List<AuditLog> getRecentAuditLogs() {
        return auditLogMapper.getRecentLogs();
    }
}
package com.finance.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.finance.domain.AuditLog;
import com.finance.mapper.AuditLogMapper;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogMapper auditLogMapper;

    @AfterReturning("execution(* com.finance.service.LoanReviewService.deleteApplication(..))")
    public void logDelete(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String id = (String) args[0];

        AuditLog log = new AuditLog();
        log.setActionName("DELETE_APPLICATION");
        log.setTargetId(id);
        log.setActionDetail("Application Soft Deleted");
        auditLogMapper.insertLog(log);
    }

    @AfterReturning("execution(* com.finance.service.LoanReviewService.updateStatusBulk(..))")
    public void logBulkUpdate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String status = (String) args[0];
        List<?> ids = (List<?>) args[1];

        AuditLog log = new AuditLog();
        log.setActionName("BULK_STATUS_UPDATE");
        log.setTargetId("MULTIPLE");
        log.setActionDetail("Status updated to " + status + " for " + ids.size() + " items");
        auditLogMapper.insertLog(log);
    }
    
 // LoanMapper의 insertApplication(대출신청)이 성공적으로 끝날 때마다 아래 로직이 자동 실행됨
    @AfterReturning("execution(* com.finance.mapper.LoanMapper.insertApplication(..))")
    public void logAfterApply(JoinPoint joinPoint) {
        try {
            Map<String, Object> log = new HashMap<>();
            log.put("actionName", "대출 신청 접수");
            log.put("targetId", "SYSTEM");
            log.put("actionDetail", "신규 대출 신청이 성공적으로 접수되었습니다.");
            
            auditLogMapper.insertAuditLog(log);
        } catch (Exception e) {
            System.err.println("감사 로그 저장 실패: " + e.getMessage());
        }
    }
}
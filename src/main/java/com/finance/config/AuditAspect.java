package com.finance.config;

import com.finance.domain.AuditLog;
import com.finance.mapper.AuditLogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

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
}
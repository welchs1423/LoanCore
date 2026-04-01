package com.finance.mapper;

import com.finance.domain.AuditLog;

public interface AuditLogMapper {
    void insertLog(AuditLog log);
}
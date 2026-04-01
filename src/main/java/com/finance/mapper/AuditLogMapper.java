package com.finance.mapper;

import com.finance.domain.AuditLog;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogMapper {
    // 필드명을 actionName, targetId, actionDetail로 변경하여 매핑
    @Insert("INSERT INTO audit_logs (action, target, actor) VALUES (#{actionName}, #{targetId}, #{actionDetail})")
    void insertLog(AuditLog log);
}
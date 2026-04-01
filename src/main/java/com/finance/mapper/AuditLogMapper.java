package com.finance.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.finance.domain.AuditLog;

@Repository
public interface AuditLogMapper {
    // 필드명을 actionName, targetId, actionDetail로 변경하여 매핑
    @Insert("INSERT INTO audit_logs (action, target, actor) VALUES (#{actionName}, #{targetId}, #{actionDetail})")
    void insertLog(AuditLog log);
    
    @Select("SELECT actionName, targetId, actionDetail FROM audit_logs ORDER BY id DESC LIMIT 50")
    List<AuditLog> getRecentLogs();
}
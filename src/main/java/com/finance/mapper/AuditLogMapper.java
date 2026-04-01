package com.finance.mapper;

import com.finance.domain.AuditLog;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AuditLogMapper {
    @Insert("INSERT INTO audit_logs (action_name, target_id, action_detail) VALUES (#{actionName}, #{targetId}, #{actionDetail})")
    void insertLog(AuditLog log);

    @Select("SELECT action_name as actionName, target_id as targetId, action_detail as actionDetail FROM audit_logs ORDER BY id DESC LIMIT 50")
    List<AuditLog> getRecentLogs();
}
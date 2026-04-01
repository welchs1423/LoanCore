package com.finance.mapper;

import com.finance.domain.AuditLog; // 이 위치에 도메인이 있어야 합니다.
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogMapper {
    @Insert("INSERT INTO audit_logs (action, target, actor) VALUES (#{action}, #{target}, #{actor})")
    void insertLog(AuditLog log); // 파라미터를 객체 타입으로 변경
}
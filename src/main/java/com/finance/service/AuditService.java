package com.finance.service;

import java.time.LocalDateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final JdbcTemplate jdbcTemplate;

    public AuditService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void logChange(String targetId, String action, String beforeValue, String afterValue, String userId) {
        String sql = "INSERT INTO AUDIT_TRAIL (TARGET_ID, ACTION_TYPE, BEFORE_VALUE, AFTER_VALUE, USER_ID, LOG_DATE) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, targetId, action, beforeValue, afterValue, userId, LocalDateTime.now());
    }
}
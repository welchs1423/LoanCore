package com.finance.service;

import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class BatchMonitoringService {

    private final JdbcTemplate jdbcTemplate;

    public BatchMonitoringService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void logBatchResult(String batchName, String status, String errorMessage) {
        String sql = "INSERT INTO BATCH_LOG (BATCH_NAME, STATUS, ERROR_MESSAGE, EXECUTION_TIME) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, batchName, status, errorMessage, LocalDateTime.now());
    }
}
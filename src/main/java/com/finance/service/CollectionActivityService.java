package com.finance.service;

import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CollectionActivityService {

    private final JdbcTemplate jdbcTemplate;

    public CollectionActivityService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void logActivity(String applicationId, String collectorId, String activityType, String memo) {
        String sql = "INSERT INTO COLLECTION_ACTIVITY (APPLICATION_ID, COLLECTOR_ID, ACTIVITY_TYPE, MEMO, ACTIVITY_DATE) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, applicationId, collectorId, activityType, memo, LocalDateTime.now());
    }
}
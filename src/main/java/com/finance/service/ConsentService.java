package com.finance.service;

import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsentService {

    private final JdbcTemplate jdbcTemplate;

    public ConsentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void recordConsent(String customerId, String ipAddress, String signature) {
        String sql = "INSERT INTO CONSENT_HISTORY (CUSTOMER_ID, IP_ADDRESS, SIGNATURE, CONSENT_DATE) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, customerId, ipAddress, signature, LocalDateTime.now());
    }
}
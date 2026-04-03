package com.finance.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RepaymentSchedulePersistenceService {

    private final JdbcTemplate jdbcTemplate;

    public RepaymentSchedulePersistenceService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveScheduleRecord(String applicationId, int installmentMonth, BigDecimal principal, BigDecimal interest, LocalDate dueDate) {
        String sql = "INSERT INTO LOAN_SCHEDULE (APPLICATION_ID, INSTALLMENT_MONTH, PRINCIPAL, INTEREST, DUE_DATE, STATUS) VALUES (?, ?, ?, ?, ?, 'WAITING')";
        jdbcTemplate.update(sql, applicationId, installmentMonth, principal, interest, dueDate);
    }
}
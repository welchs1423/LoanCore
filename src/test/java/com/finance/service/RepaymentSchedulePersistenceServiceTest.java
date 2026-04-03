package com.finance.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

public class RepaymentSchedulePersistenceServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private RepaymentSchedulePersistenceService persistenceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveScheduleRecord() {
        LocalDate dueDate = LocalDate.of(2026, 5, 1);
        persistenceService.saveScheduleRecord("L001", 1, new BigDecimal("100000"), new BigDecimal("5000"), dueDate);

        verify(jdbcTemplate).update(
            anyString(),
            eq("L001"),
            eq(1),
            eq(new BigDecimal("100000")),
            eq(new BigDecimal("5000")),
            eq(dueDate)
        );
    }
}
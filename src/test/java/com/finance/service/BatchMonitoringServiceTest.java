package com.finance.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

public class BatchMonitoringServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BatchMonitoringService batchMonitoringService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogBatchResult() {
        batchMonitoringService.logBatchResult("DailyInterestBatch", "SUCCESS", null);

        verify(jdbcTemplate).update(
            anyString(),
            eq("DailyInterestBatch"),
            eq("SUCCESS"),
            eq((String) null),
            any(LocalDateTime.class)
        );
    }
}
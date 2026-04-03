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

public class ConsentServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ConsentService consentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRecordConsent() {
        consentService.recordConsent("CUST01", "192.168.0.1", "SIGNATURE_DATA");

        verify(jdbcTemplate).update(
            anyString(),
            eq("CUST01"),
            eq("192.168.0.1"),
            eq("SIGNATURE_DATA"),
            any(LocalDateTime.class)
        );
    }
}
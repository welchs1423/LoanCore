package com.finance.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

public class PreInquiryServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PreInquiryService preInquiryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRecordInquiry() {
        preInquiryService.recordInquiry("CUST01", 850, 4.2, new BigDecimal("50000000"));

        verify(jdbcTemplate).update(
            anyString(),
            eq("CUST01"),
            eq(850),
            eq(4.2),
            eq(new BigDecimal("50000000")),
            any(LocalDateTime.class)
        );
    }
}
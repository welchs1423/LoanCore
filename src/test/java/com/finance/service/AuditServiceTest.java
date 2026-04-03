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

public class AuditServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuditService auditService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogChange() {
        auditService.logChange("L001", "STATUS_CHANGE", "PENDING", "APPROVED", "ADMIN_01");

        verify(jdbcTemplate).update(
            anyString(),
            eq("L001"),
            eq("STATUS_CHANGE"),
            eq("PENDING"),
            eq("APPROVED"),
            eq("ADMIN_01"),
            any(LocalDateTime.class)
        );
    }
}
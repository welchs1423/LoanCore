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

public class CollectionActivityServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CollectionActivityService collectionActivityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogActivity() {
        collectionActivityService.logActivity("L001", "EMP999", "CALL", "고객 부재중");

        verify(jdbcTemplate).update(
            anyString(),
            eq("L001"),
            eq("EMP999"),
            eq("CALL"),
            eq("고객 부재중"),
            any(LocalDateTime.class)
        );
    }
}
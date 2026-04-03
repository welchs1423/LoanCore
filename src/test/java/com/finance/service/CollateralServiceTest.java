package com.finance.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

public class CollateralServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CollateralService collateralService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterCollateral() {
        collateralService.registerCollateral("L001", "REAL_ESTATE", new BigDecimal("500000000"));

        verify(jdbcTemplate).update(
            anyString(),
            eq("L001"),
            eq("REAL_ESTATE"),
            eq(new BigDecimal("500000000"))
        );
    }
}
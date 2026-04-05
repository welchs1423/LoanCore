package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.finance.mapper.LoanMapper;

public class DashboardCacheServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private DashboardCacheService dashboardCacheService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDailyTotalLoanAmount() {
        when(loanMapper.getTotalLoanAmountByDate("2026-04-05")).thenReturn(new BigDecimal("500000000"));
        BigDecimal total = dashboardCacheService.getDailyTotalLoanAmount("2026-04-05");
        assertEquals(new BigDecimal("500000000"), total);
    }
}
package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

public class LoanCancellationServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanCancellationService cancellationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCancelLoan() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L001");
        app.setAmount(new BigDecimal("10000000"));

        when(loanMapper.selectApplicationById("L001")).thenReturn(app);

        cancellationService.cancelLoan("L001");

        assertEquals("CANCELED", app.getStatusCode());
        assertEquals(BigDecimal.ZERO, app.getAmount());
        verify(loanMapper).updateApplication(app);
    }
}
package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

public class LoanDisbursementServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanDisbursementService disbursementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDisburseLoan() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L001");
        app.setStatusCode("APPROVED");

        when(loanMapper.selectApplicationById("L001")).thenReturn(app);

        disbursementService.disburseLoan("L001", "004", "1234567890");

        assertEquals("ACTIVE", app.getStatusCode());
        verify(loanMapper).updateApplication(app);
    }
}
package com.finance.service;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoanReviewServiceTest {

    @InjectMocks
    private LoanReviewService loanReviewService;

    @Mock
    private LoanMapper loanMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReviewLoan() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L12345");
        app.setStatusCode("PENDING");

        String result = loanReviewService.reviewLoan(app);

        assertEquals("APPROVE", result);
        assertEquals("APPROVE", app.getStatusCode());
    }

    @Test
    public void testApproveLoan() {
        LoanApplication mockApp = new LoanApplication();
        mockApp.setApplicationId("L12345");
        mockApp.setStatusCode("PENDING");

        when(loanMapper.selectApplicationById("L12345")).thenReturn(mockApp);

        loanReviewService.approveLoan("L12345");

        verify(loanMapper).updateApplication(any(LoanApplication.class));
        assertEquals("APPROVED", mockApp.getStatusCode());
    }
}
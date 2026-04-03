package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

public class RateReductionServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private RateReductionService rateReductionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessReductionRequest_Success() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L001");
        app.setStatusCode("ACTIVE");

        when(loanMapper.selectApplicationById("L001")).thenReturn(app);

        boolean result = rateReductionService.processReductionRequest("L001", 700, 760);
        
        assertTrue(result);
        verify(loanMapper).updateApplication(app);
    }

    @Test
    public void testProcessReductionRequest_Fail_NotEnoughScore() {
        boolean result = rateReductionService.processReductionRequest("L002", 700, 720);
        assertFalse(result);
    }
}
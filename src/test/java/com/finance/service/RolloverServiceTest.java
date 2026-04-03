package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

public class RolloverServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @Mock
    private InterestRateService interestRateService;

    @InjectMocks
    private RolloverService rolloverService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessRollover() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L001");
        app.setStatusCode("APPROVED");

        when(loanMapper.selectApplicationById("L001")).thenReturn(app);
        when(interestRateService.calculateApplicableRate(850)).thenReturn(4.2);

        rolloverService.processRollover("L001", 850);

        assertEquals("ROLLOVER", app.getStatusCode());
        verify(loanMapper).updateApplication(app);
    }
}
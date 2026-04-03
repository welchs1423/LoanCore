package com.finance.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

public class PenaltySchedulerTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private PenaltyScheduler penaltyScheduler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testApplyOverduePenalty() {
        LoanApplication app = new LoanApplication();
        app.setAmount(new BigDecimal("10000"));
        
        when(loanMapper.searchApplicationsDynamic(null, "OVERDUE", null, null))
            .thenReturn(Arrays.asList(app));

        penaltyScheduler.applyOverduePenalty();

        assertEquals(new BigDecimal("10005.0000"), app.getAmount());
        verify(loanMapper).updateApplication(app);
    }
}
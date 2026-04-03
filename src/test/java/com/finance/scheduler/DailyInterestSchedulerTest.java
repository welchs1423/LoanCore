package com.finance.scheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

public class DailyInterestSchedulerTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private DailyInterestScheduler scheduler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAccrueDailyInterest() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L001");
        app.setAmount(new BigDecimal("10000000"));

        when(loanMapper.searchApplicationsDynamic(null, "ACTIVE", null, null))
            .thenReturn(Arrays.asList(app));

        scheduler.accrueDailyInterest();

        verify(loanMapper).searchApplicationsDynamic(null, "ACTIVE", null, null);
    }
}
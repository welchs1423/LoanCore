package com.finance.scheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;
import com.finance.service.ExternalCreditApiClient;

public class EarlyWarningSchedulerTest {

    @Mock
    private LoanMapper loanMapper;

    @Mock
    private ExternalCreditApiClient creditApiClient;

    @InjectMocks
    private EarlyWarningScheduler earlyWarningScheduler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMonitorCreditDrops() {
        LoanApplication app = new LoanApplication();
        app.setCustomerId("CUST01");
        
        when(loanMapper.searchApplicationsDynamic(null, "APPROVED", null, null))
            .thenReturn(Arrays.asList(app));
        when(creditApiClient.fetchCreditScore("CUST01")).thenReturn(500);

        earlyWarningScheduler.monitorCreditDrops();

        verify(creditApiClient).fetchCreditScore("CUST01");
    }
}
package com.finance.service;

import static org.mockito.Mockito.verify;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

public class RefinancingServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private RefinancingService refinancingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecuteRefinancing() {
        LoanApplication app = new LoanApplication();
        refinancingService.executeRefinancing(app, "KBBank", new BigDecimal("30000000"));
        verify(loanMapper).insertApplication(app);
    }
}
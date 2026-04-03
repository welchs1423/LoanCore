package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

public class LoanLiquidationServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanLiquidationService liquidationService;

    @Test
    public void testCloseLoan() {
        MockitoAnnotations.openMocks(this);
        LoanApplication app = new LoanApplication();
        app.setAmount(BigDecimal.ZERO);
        when(loanMapper.selectApplicationById("L001")).thenReturn(app);

        String result = liquidationService.closeLoan("L001");
        assertNotNull(result);
    }
}
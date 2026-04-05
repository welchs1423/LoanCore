package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

public class NplSaleServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private NplSaleService nplSaleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSellNpl_Success() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L001");
        app.setStatusCode("OVERDUE");

        when(loanMapper.selectApplicationById("L001")).thenReturn(app);

        boolean result = nplSaleService.sellNpl("L001", "CollectionAgency_A", new BigDecimal("5000000"));
        
        assertTrue(result);
        assertEquals("SOLD", app.getStatusCode());
        verify(loanMapper).updateApplication(app);
    }

    @Test
    public void testSellNpl_Fail() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L002");
        app.setStatusCode("ACTIVE");

        when(loanMapper.selectApplicationById("L002")).thenReturn(app);

        boolean result = nplSaleService.sellNpl("L002", "CollectionAgency_A", new BigDecimal("5000000"));
        
        assertFalse(result);
    }
}
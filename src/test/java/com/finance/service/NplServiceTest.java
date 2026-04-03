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

public class NplServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private NplService nplService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessWriteOff() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L001");
        app.setStatusCode("OVERDUE");

        when(loanMapper.selectApplicationById("L001")).thenReturn(app);

        nplService.processWriteOff("L001");

        assertEquals("WRITTEN_OFF", app.getStatusCode());
        verify(loanMapper).updateApplication(app);
    }

    @Test
    public void testProcessSaleToAgency() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L002");
        app.setStatusCode("WRITTEN_OFF");

        when(loanMapper.selectApplicationById("L002")).thenReturn(app);

        nplService.processSaleToAgency("L002", "행복추심컨설팅");

        assertEquals("SOLD_NPL", app.getStatusCode());
        verify(loanMapper).updateApplication(app);
    }
}
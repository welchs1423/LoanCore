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

public class RehabilitationServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private RehabilitationService rehabilitationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterBankruptcy() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L002");
        app.setStatusCode("OVERDUE");

        when(loanMapper.selectApplicationById("L002")).thenReturn(app);

        rehabilitationService.registerBankruptcy("L002");

        assertEquals("BANKRUPT", app.getStatusCode());
        verify(loanMapper).updateApplication(app);
    }
}
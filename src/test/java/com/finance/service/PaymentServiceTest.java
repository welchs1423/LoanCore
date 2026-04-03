package com.finance.service;

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

public class PaymentServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessPayment() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L001");
        app.setAmount(new BigDecimal("10000"));
        app.setStatusCode("APPROVED");

        when(loanMapper.selectApplicationById("L001")).thenReturn(app);

        paymentService.processPayment("L001", new BigDecimal("4000"));

        assertEquals(new BigDecimal("6000"), app.getAmount());
        verify(loanMapper).updateApplication(app);
    }

    @Test
    public void testProcessOverdueLoans() {
        LoanApplication app = new LoanApplication();
        app.setStatusCode("APPROVED");

        when(loanMapper.searchApplicationsDynamic(null, "APPROVED", null, null))
            .thenReturn(Arrays.asList(app));

        paymentService.processOverdueLoans();

        assertEquals("OVERDUE", app.getStatusCode());
        verify(loanMapper).updateApplication(app);
    }
}
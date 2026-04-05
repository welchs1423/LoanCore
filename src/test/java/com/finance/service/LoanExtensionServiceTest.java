package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

public class LoanExtensionServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanExtensionService loanExtensionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExtendMaturity() {
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L001");
        app.setStatusCode("ACTIVE");

        when(loanMapper.selectApplicationById("L001")).thenReturn(app);

        boolean result = loanExtensionService.extendMaturity("L001", 12);
        
        assertTrue(result);
        assertEquals("EXTENDED", app.getStatusCode());
        verify(loanMapper).updateApplication(app);
    }
}
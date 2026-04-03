package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

public class CertificateServiceTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private CertificateService certificateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIssueBalanceCertificate() {
        LoanApplication app = new LoanApplication();
        app.setAmount(new BigDecimal("5000000"));
        when(loanMapper.selectApplicationById("L001")).thenReturn(app);

        String certId = certificateService.issueBalanceCertificate("L001");
        
        assertNotNull(certId);
        assertTrue(certId.startsWith("CERT_"));
    }
}
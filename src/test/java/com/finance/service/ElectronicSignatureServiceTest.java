package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

public class ElectronicSignatureServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ElectronicSignatureService signatureService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateAndSaveSignature() {
        String hash = signatureService.generateSignatureHash("CUST01", "DOC01");
        assertNotNull(hash);

        signatureService.saveSignature("L001", "CUST01", hash);
        verify(jdbcTemplate).update(
            anyString(),
            eq("L001"),
            eq("CUST01"),
            eq(hash),
            any(LocalDateTime.class)
        );
    }
}
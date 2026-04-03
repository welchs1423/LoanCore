package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountVerificationServiceTest {

    private AccountVerificationService verificationService;

    @BeforeEach
    public void setUp() {
        verificationService = new AccountVerificationService();
    }

    @Test
    public void testSendAndVerifyCode() {
        String accountNumber = "123-456-789";
        String code = verificationService.sendOneWon(accountNumber);

        assertNotNull(code);
        assertTrue(verificationService.verifyCode(accountNumber, code));
        assertFalse(verificationService.verifyCode(accountNumber, code));
    }
}
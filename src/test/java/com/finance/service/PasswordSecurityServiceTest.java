package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PasswordSecurityServiceTest {

    private PasswordSecurityService securityService;

    @BeforeEach
    public void setUp() {
        securityService = new PasswordSecurityService();
    }

    @Test
    public void testEncryptPassword() {
        String encrypted = securityService.encryptPassword("password123");
        assertNotNull(encrypted);
    }

    @Test
    public void testCheckLoginFailure() {
        String userId = "user01";
        assertFalse(securityService.checkLoginFailure(userId));
        assertFalse(securityService.checkLoginFailure(userId));
        assertFalse(securityService.checkLoginFailure(userId));
        assertFalse(securityService.checkLoginFailure(userId));
        assertTrue(securityService.checkLoginFailure(userId));
    }
}
package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RateLimiterServiceTest {

    private RateLimiterService rateLimiterService;

    @BeforeEach
    public void setUp() {
        rateLimiterService = new RateLimiterService();
    }

    @Test
    public void testIsAllowed() {
        String apiKey = "API_KEY_001";
        for (int i = 0; i < 100; i++) {
            assertTrue(rateLimiterService.isAllowed(apiKey));
        }
        assertFalse(rateLimiterService.isAllowed(apiKey));
    }
}
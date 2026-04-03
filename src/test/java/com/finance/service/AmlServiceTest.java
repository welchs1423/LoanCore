package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AmlServiceTest {

    private AmlService amlService;

    @BeforeEach
    public void setUp() {
        amlService = new AmlService();
    }

    @Test
    public void testIsBlacklisted() {
        assertTrue(amlService.isBlacklisted("SUSPECT_001"));
        assertFalse(amlService.isBlacklisted("NORMAL_USER"));
    }

    @Test
    public void testAddToBlacklist() {
        amlService.addToBlacklist("NEW_HACKER");
        assertTrue(amlService.isBlacklisted("NEW_HACKER"));
    }
}
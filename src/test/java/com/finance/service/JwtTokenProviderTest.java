package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Test
    public void testGenerateAndValidateToken() {
        String token = jwtTokenProvider.generateToken("user123");
        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
    }
}
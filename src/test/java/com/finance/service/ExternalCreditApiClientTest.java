package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExternalCreditApiClientTest {

    private ExternalCreditApiClient apiClient;

    @BeforeEach
    public void setUp() {
        apiClient = new ExternalCreditApiClient();
    }

    @Test
    public void testFetchCreditScore() {
        int score = apiClient.fetchCreditScore("CUST01");
        assertEquals(850, score);
    }
}
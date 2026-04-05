package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreditBureauClientTest {

    private CreditBureauClient creditBureauClient;

    @BeforeEach
    public void setUp() {
        creditBureauClient = new CreditBureauClient();
    }

    @Test
    public void testFetchCreditScore() {
        int score = creditBureauClient.fetchCreditScore("900101-1234567");
        assertEquals(850, score);
    }

    @Test
    public void testFetchCreditScore_EmptyResidentNumber() {
        int score = creditBureauClient.fetchCreditScore("");
        assertEquals(0, score);
    }
}
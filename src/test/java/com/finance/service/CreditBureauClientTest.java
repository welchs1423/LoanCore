package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.finance.mapper.LoanMapper;

public class CreditBureauClientTest {

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private CreditBureauClient creditBureauClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchCreditScore() {
        when(loanMapper.getMockCreditScore("900101-1234567")).thenReturn(850);
        int score = creditBureauClient.fetchCreditScore("900101-1234567");
        assertEquals(850, score);
    }

    @Test
    public void testFetchCreditScore_EmptyResidentNumber() {
        int score = creditBureauClient.fetchCreditScore("");
        assertEquals(0, score);
    }
}
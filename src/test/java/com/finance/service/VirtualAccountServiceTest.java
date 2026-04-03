package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

public class VirtualAccountServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private VirtualAccountService virtualAccountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIssueVirtualAccount() {
        String accountNumber = virtualAccountService.issueVirtualAccount("L001", "088");
        assertNotNull(accountNumber);

        verify(jdbcTemplate).update(
            anyString(),
            eq("L001"),
            eq("088"),
            eq(accountNumber)
        );
    }
}
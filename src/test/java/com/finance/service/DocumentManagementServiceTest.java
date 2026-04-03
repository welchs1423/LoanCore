package com.finance.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

public class DocumentManagementServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private DocumentManagementService documentManagementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterFileMeta() {
        documentManagementService.registerFileMeta("L001", "ID_CARD", "https://s3/path", 1024L);
        verify(jdbcTemplate).update(anyString(), eq("L001"), eq("ID_CARD"), eq("https://s3/path"), eq(1024L));
    }
}
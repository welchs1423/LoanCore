package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

public class PushMessageServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PushMessageService pushMessageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendPushNotification() {
        String msgId = pushMessageService.sendPushNotification("user123", "대출 승인", "대출이 승인되었습니다.");
        
        assertNotNull(msgId);
        verify(jdbcTemplate).update(
            anyString(), 
            eq(msgId), 
            eq("user123"), 
            eq("대출 승인"), 
            eq("대출이 승인되었습니다."), 
            any(LocalDateTime.class)
        );
    }
}
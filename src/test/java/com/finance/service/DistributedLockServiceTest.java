package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class DistributedLockServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private DistributedLockService distributedLockService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void testAcquireLock() {
        when(valueOperations.setIfAbsent(anyString(), anyString(), anyLong(), eq(TimeUnit.SECONDS))).thenReturn(true);
        
        boolean acquired = distributedLockService.acquireLock("LOCK_L001", "UUID", 10);
        assertTrue(acquired);
        verify(valueOperations).setIfAbsent("LOCK_L001", "UUID", 10L, TimeUnit.SECONDS);
    }

    @Test
    public void testReleaseLock() {
        distributedLockService.releaseLock("LOCK_L001");
        verify(redisTemplate).delete("LOCK_L001");
    }
}
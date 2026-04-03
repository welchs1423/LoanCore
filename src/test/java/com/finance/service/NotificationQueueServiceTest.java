package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class NotificationQueueServiceTest {

    @Test
    public void testQueueProcess() {
        NotificationQueueService service = new NotificationQueueService();
        service.enqueueNotification("Test Message");
        assertEquals("Test Message", service.processNext());
    }
}
package com.finance.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Service;

@Service
public class NotificationQueueService {

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public void enqueueNotification(String message) {
        queue.offer(message);
    }

    public String processNext() {
        return queue.poll();
    }
}
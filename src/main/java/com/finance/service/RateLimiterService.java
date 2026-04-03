package com.finance.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final Map<String, Integer> requestCounts = new HashMap<>();
    private final Map<String, Long> windowStartTimes = new HashMap<>();
    private static final int MAX_REQUESTS_PER_MINUTE = 100;

    public boolean isAllowed(String apiKey) {
        long currentTime = System.currentTimeMillis();
        long windowStart = windowStartTimes.getOrDefault(apiKey, currentTime);
        
        if (currentTime - windowStart > 60000) {
            windowStartTimes.put(apiKey, currentTime);
            requestCounts.put(apiKey, 0);
        }
        
        int count = requestCounts.getOrDefault(apiKey, 0);
        if (count >= MAX_REQUESTS_PER_MINUTE) {
            return false;
        }
        
        requestCounts.put(apiKey, count + 1);
        return true;
    }
}
package com.finance.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PasswordSecurityService {

    private final Map<String, Integer> failureCountMap = new HashMap<>();
    private static final int MAX_FAILURES = 5;

    public String encryptPassword(String rawPassword) {
        return String.valueOf(rawPassword.hashCode());
    }

    public boolean checkLoginFailure(String userId) {
        int currentFailures = failureCountMap.getOrDefault(userId, 0) + 1;
        failureCountMap.put(userId, currentFailures);
        
        return currentFailures >= MAX_FAILURES;
    }

    public void resetFailureCount(String userId) {
        failureCountMap.remove(userId);
    }
}
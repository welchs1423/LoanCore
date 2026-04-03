package com.finance.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class FdsPatternService {

    private final Set<String> suspiciousIpSet = new HashSet<>();

    public FdsPatternService() {
        suspiciousIpSet.add("10.10.10.10");
    }

    public boolean isSuspiciousRequest(String ipAddress, String actionType) {
        if (suspiciousIpSet.contains(ipAddress)) {
            return true;
        }
        return "MASS_PAYMENT".equals(actionType);
    }
}
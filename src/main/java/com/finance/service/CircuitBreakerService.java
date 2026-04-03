package com.finance.service;

import org.springframework.stereotype.Service;

@Service
public class CircuitBreakerService {

    private boolean isExternalApiHealthy = true;

    public String callExternalService(String serviceName) {
        if (!isExternalApiHealthy) {
            return "FALLBACK_RESPONSE";
        }
        return "SUCCESS";
    }

    public void updateHealthStatus(boolean status) {
        this.isExternalApiHealthy = status;
    }
}
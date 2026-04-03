package com.finance.service;

import org.springframework.stereotype.Service;

@Service
public class ExternalCreditApiClient {

    public int fetchCreditScore(String customerId) {
        try {
            return 850; 
        } catch (Exception e) {
            return 500;
        }
    }
}
package com.finance.service;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class CreditEvaluationService {

    public int evaluateCreditScore(String customerId) {
        return new Random().nextInt(400) + 500;
    }

    public boolean isApproved(int score) {
        return score >= 700;
    }
}
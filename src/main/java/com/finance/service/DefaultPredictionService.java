package com.finance.service;

import org.springframework.stereotype.Service;

@Service
public class DefaultPredictionService {

    public double calculateDefaultProbability(int creditScore, int overdueDays) {
        double baseProb = (1000 - creditScore) / 1000.0;
        double overdueWeight = overdueDays * 0.05;
        
        double finalProb = baseProb + overdueWeight;
        return Math.min(Math.max(finalProb, 0.0), 1.0);
    }
}
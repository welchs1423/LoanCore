package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DefaultPredictionServiceTest {

    @Test
    public void testCalculateDefaultProbability() {
        DefaultPredictionService service = new DefaultPredictionService();
        double prob = service.calculateDefaultProbability(700, 10);
        assertTrue(prob >= 0.0 && prob <= 1.0);
    }
}
package com.finance.service;

import org.springframework.stereotype.Service;

@Service
public class KycOcrService {

    public String extractResidentNumber(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            throw new IllegalArgumentException("Invalid image path");
        }
        return "900101-1234567";
    }

    public boolean verifyIdentity(String extractedNumber, String inputNumber) {
        return extractedNumber != null && extractedNumber.equals(inputNumber);
    }
}
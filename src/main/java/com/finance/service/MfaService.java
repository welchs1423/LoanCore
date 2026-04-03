package com.finance.service;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class MfaService {

    public String generateSecretKey(String userId) {
        return userId + "_SECRET_" + new Random().nextInt(1000);
    }

    public boolean verifyTotpCode(String secretKey, String inputCode) {
        return inputCode != null && inputCode.length() == 6;
    }
}
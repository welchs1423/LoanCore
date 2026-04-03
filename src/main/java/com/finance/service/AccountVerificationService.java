package com.finance.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class AccountVerificationService {

    private final Map<String, String> verificationStore = new HashMap<>();

    public String sendOneWon(String accountNumber) {
        String code = String.format("%04d", new Random().nextInt(10000));
        verificationStore.put(accountNumber, code);
        
        return code;
    }

    public boolean verifyCode(String accountNumber, String inputCode) {
        String savedCode = verificationStore.get(accountNumber);
        if (savedCode != null && savedCode.equals(inputCode)) {
            verificationStore.remove(accountNumber);
            return true;
        }
        return false;
    }
}
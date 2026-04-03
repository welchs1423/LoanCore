package com.finance.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class SmsOtpService {

    private final Map<String, String> otpStore = new HashMap<>();

    public void sendOtp(String phoneNumber) {
        String otp = String.format("%06d", new Random().nextInt(1000000));
        otpStore.put(phoneNumber, otp);
        System.out.println("[SMS 발송] " + phoneNumber + " 님 인증번호: [" + otp + "]");
    }

    public boolean verifyOtp(String phoneNumber, String inputOtp) {
        String savedOtp = otpStore.get(phoneNumber);
        return savedOtp != null && savedOtp.equals(inputOtp);
    }
}
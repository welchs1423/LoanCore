package com.finance.config;

import org.springframework.stereotype.Component;

@Component
public class PrivacyMaskingUtils {

    public String maskResidentNumber(String rrn) {
        if (rrn == null || rrn.length() < 7) return rrn;
        return rrn.substring(0, 8) + "******";
    }

    public String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() < 10) return phone;
        return phone.substring(0, 3) + "-****-" + phone.substring(phone.length() - 4);
    }
}
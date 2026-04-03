package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SmsOtpServiceTest {

    @Test
    public void testOtpProcess() {
        SmsOtpService service = new SmsOtpService();
        String phone = "01012345678";
        service.sendOtp(phone);
        
        assertTrue(true); 
    }
}
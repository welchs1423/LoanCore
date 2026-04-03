package com.finance.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PrivacyMaskingUtilsTest {

    @Test
    public void testMasking() {
        PrivacyMaskingUtils utils = new PrivacyMaskingUtils();
        assertEquals("900101-1******", utils.maskResidentNumber("900101-1234567"));
        assertEquals("010-****-5678", utils.maskPhoneNumber("01012345678"));
    }
}
package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class FdsPatternServiceTest {

    @Test
    public void testIsSuspiciousRequest() {
        FdsPatternService service = new FdsPatternService();
        assertTrue(service.isSuspiciousRequest("10.10.10.10", "LOGIN"));
        assertTrue(service.isSuspiciousRequest("1.1.1.1", "MASS_PAYMENT"));
    }
}
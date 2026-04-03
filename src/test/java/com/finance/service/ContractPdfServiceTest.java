package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class ContractPdfServiceTest {

    @Test
    public void testGenerateContract() {
        ContractPdfService service = new ContractPdfService();
        File file = service.generateContract("홍길동", new BigDecimal("50000000"), 4.5);
        assertTrue(file.exists());
        file.delete();
    }
}
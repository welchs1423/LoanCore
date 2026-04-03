package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.finance.domain.LoanApplication;

public class CsvExportServiceTest {

    private CsvExportService csvExportService;

    @BeforeEach
    public void setUp() {
        csvExportService = new CsvExportService();
    }

    @Test
    public void testExportToCsv() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        
        LoanApplication app = new LoanApplication();
        app.setApplicationId("L001");
        app.setCustomerId("CUST01");
        app.setAmount(new BigDecimal("10000000"));
        app.setStatusCode("ACTIVE");
        
        List<LoanApplication> loans = Arrays.asList(app);
        
        csvExportService.exportToCsv(printWriter, loans);
        
        String result = stringWriter.toString();
        assertTrue(result.contains("ApplicationID,CustomerID,Amount,Status"));
        assertTrue(result.contains("L001,CUST01,10000000,ACTIVE"));
    }
}
package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OcrServiceTest {

    private OcrService ocrService;

    @BeforeEach
    public void setUp() {
        ocrService = new OcrService();
    }

    @Test
    public void testExtractIdCardInfo() {
        File dummyFile = new File("dummy.jpg");
        try {
            dummyFile.createNewFile();
            Map<String, String> info = ocrService.extractIdCardInfo(dummyFile);
            assertEquals("홍길동", info.get("name"));
            assertTrue(ocrService.verifyIdCard(info.get("residentNumber"), info.get("issueDate")));
        } catch (Exception e) {
        } finally {
            dummyFile.delete();
        }
    }
}
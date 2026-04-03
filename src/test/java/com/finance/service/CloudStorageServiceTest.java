package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CloudStorageServiceTest {

    private CloudStorageService cloudStorageService;

    @BeforeEach
    public void setUp() {
        cloudStorageService = new CloudStorageService();
    }

    @Test
    public void testUploadDocument() throws Exception {
        File dummyFile = new File("test_doc.pdf");
        dummyFile.createNewFile();
        
        String url = cloudStorageService.uploadDocument(dummyFile);
        assertTrue(url.contains("amazonaws.com"));
        
        dummyFile.delete();
    }
}
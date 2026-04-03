package com.finance.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class OcrService {

    public Map<String, String> extractIdCardInfo(File imageFile) {
        Map<String, String> result = new HashMap<>();
        if (imageFile != null && imageFile.exists()) {
            result.put("name", "홍길동");
            result.put("residentNumber", "900101-1234567");
            result.put("issueDate", "2020-01-01");
        }
        return result;
    }

    public boolean verifyIdCard(String residentNumber, String issueDate) {
        return residentNumber != null && issueDate != null;
    }
}
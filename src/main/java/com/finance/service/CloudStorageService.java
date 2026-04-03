package com.finance.service;

import java.io.File;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CloudStorageService {

    public String uploadDocument(File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getName();
        return "https://s3.ap-northeast-2.amazonaws.com/loancore-bucket/" + uniqueFileName;
    }
}
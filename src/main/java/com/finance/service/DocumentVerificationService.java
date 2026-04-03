package com.finance.service;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class DocumentVerificationService {

    public boolean detectForgery(File document) {
        if (document == null || !document.exists()) {
            return false; // 파일이 없으면 검증 불가
        }
        
        // 모의 위변조 탐지 로직: 파일 이름에 'edited'나 'photoshop'이 들어가면 위조로 간주
        String fileName = document.getName().toLowerCase();
        if (fileName.contains("edited") || fileName.contains("photoshop")) {
            return true; // 위변조 탐지됨
        }
        
        return false; // 정상 문서
    }
}
package com.finance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class FileDownloadController {

    private static final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);
    
    // 기존 LoanWebController에서 사용하던 경로와 동일하게 설정
    private final String uploadPath = "C:/upload";

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName) {
        String traceId = MDC.get("traceId");
        logger.info("[{}] 파일 다운로드 요청 시작: {}", traceId, fileName);

        File file = new File(uploadPath + File.separator + fileName);

        if (!file.exists()) {
            logger.warn("[{}] 파일을 찾을 수 없음: {}", traceId, file.getAbsolutePath());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Resource resource = new FileSystemResource(file);
        
        // 한글 파일명 깨짐 방지 처리
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", encodedFileName);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
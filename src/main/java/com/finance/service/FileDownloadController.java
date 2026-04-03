package com.finance.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class FileDownloadController {

    private static final String UPLOAD_DIR = System.getProperty("os.name").toLowerCase().contains("win") 
            ? "C:/upload/loan_docs/" 
            : "/usr/local/tomcat/upload/loan_docs/";

    @GetMapping({"/download", "/admin/file/download"})
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName) {
        try {
            File file = new File(UPLOAD_DIR + fileName);
            if (!file.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Resource resource = new FileSystemResource(file);
            String encodedFileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
            
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"");
            
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
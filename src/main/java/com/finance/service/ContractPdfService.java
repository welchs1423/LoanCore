package com.finance.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class ContractPdfService {

    public File generateContract(String customerName, BigDecimal amount, double rate) {
        File pdfFile = new File("contract_" + System.currentTimeMillis() + ".pdf");
        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            // 실제 iText 라이브러리 사용 시: Document document = new Document(); 
            // 현재는 파일 생성 및 텍스트 데이터 기록 모의 로직
            String content = "대출 약정서\n성함: " + customerName + "\n금액: " + amount + "\n금리: " + rate + "%";
            fos.write(content.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("PDF 생성 실패", e);
        }
        return pdfFile;
    }
}
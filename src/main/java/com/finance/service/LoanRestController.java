package com.finance.service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finance.config.JwtProvider;
import com.finance.domain.LoanApplication;
import com.finance.domain.LoanMemo;
import com.finance.mapper.LoanMemoMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@RestController
@RequestMapping("/api")
public class LoanRestController {

    @Autowired
    private LoanReviewService reviewService;

    @Autowired
    private LoanMemoMapper memoMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/auth/token")
    public Map<String, Object> generateToken(@RequestParam("adminId") String adminId) {
        String token = jwtProvider.createToken(adminId);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    @GetMapping("/check-customer")
    public Map<String, Object> checkCustomer(@RequestParam("customerId") String customerId) {
        List<LoanApplication> existingApps = reviewService.searchApplications(customerId, null, null, null);
        boolean hasPending = false;
        for (LoanApplication app : existingApps) {
            if ("PENDING".equals(app.getStatusCode()) || "APPROVE".equals(app.getStatusCode())) {
                hasPending = true;
                break;
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("exists", hasPending);
        response.put("message", hasPending ? "이미 진행 중이거나 승인된 대출 건이 존재합니다." : "신청 가능한 고객입니다.");
        return response;
    }

    @GetMapping("/memos")
    public List<LoanMemo> getMemos(@RequestParam("applicationId") String applicationId) {
        return memoMapper.selectMemos(applicationId);
    }

    @PostMapping("/memos")
    public Map<String, Object> addMemo(@RequestParam("applicationId") String applicationId,
                                       @RequestParam("content") String content,
                                       HttpSession session) {
        String writer = session.getAttribute("adminLogined") != null ? "관리자" : "시스템";

        LoanMemo memo = new LoanMemo();
        memo.setApplicationId(applicationId);
        memo.setWriter(writer);
        memo.setContent(content);
        memoMapper.insertMemo(memo);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    @PostMapping("/memos/delete")
    public Map<String, Object> deleteMemo(@RequestParam("memoId") int memoId) {
        memoMapper.deleteMemo(memoId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    @GetMapping("/qr")
    public ResponseEntity<byte[]> generateQRCode(@RequestParam("appId") String appId) {
        try {
            String url = "http://localhost:8080/LoanCore/detail?id=" + appId;
            int width = 150;
            int height = 150;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(pngOutputStream.toByteArray());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/check-limit")
    public Map<String, Object> checkLoanLimit(@RequestParam("amount") BigDecimal amount) {
        Map<String, Object> response = new HashMap<>();
        // 예시: 최대 대출 한도를 1억 원으로 설정
        BigDecimal maxLimit = new BigDecimal("100000000");

        if (amount.compareTo(maxLimit) > 0) {
            response.put("isAvailable", false);
            response.put("message", "최대 대출 한도(1억 원)를 초과했습니다.");
        } else {
            response.put("isAvailable", true);
            response.put("message", "대출 신청 가능한 금액입니다.");
        }
        return response;
    }
}
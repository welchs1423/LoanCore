package com.finance.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.io.ByteArrayOutputStream;

@Controller
public class DocumentController {

    @GetMapping("/api/qr/{id}")
    public ResponseEntity<byte[]> generateQR(@PathVariable("id") String id) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String url = "http://localhost:8080/LoanCore/detail/" + id;
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 150, 150);
        
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(pngOutputStream.toByteArray());
    }
}
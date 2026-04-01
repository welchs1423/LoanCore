package com.finance.service;

import com.finance.domain.LoanApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OpenApiController {

    @Autowired
    private LoanReviewService reviewService;

    @GetMapping("/loan/{id}")
    public ResponseEntity<LoanApplication> getLoanStatus(@PathVariable("id") String id) {
        LoanApplication app = reviewService.getApplicationById(id);
        
        if (app == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(app);
    }
}
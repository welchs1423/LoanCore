package com.finance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.finance.service.LoanExtensionService;
import com.finance.dto.ApiResponse;
import com.finance.dto.ExtensionRequest;

@RestController
@RequestMapping("/api/loan")
public class LoanApiController {

    private final LoanExtensionService loanExtensionService;

    public LoanApiController(LoanExtensionService loanExtensionService) {
        this.loanExtensionService = loanExtensionService;
    }

    @PostMapping("/extend")
    public ResponseEntity<ApiResponse<?>> extendLoanMaturity(@RequestBody ExtensionRequest req) {
        boolean isExtended = loanExtensionService.extendMaturity(req.getApplicationId(), req.getMonths());
        
        if (isExtended) {
            return ResponseEntity.ok(ApiResponse.success(true));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.error(400, "만기 연장 심사에 실패했습니다.", "E101"));
        }
    }
}
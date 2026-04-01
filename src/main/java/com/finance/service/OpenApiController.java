package com.finance.service;

import com.finance.domain.LoanApplication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "B2B 대출 조회 API", description = "제휴사용 대출 정보 조회 서비스")
@RestController
@RequestMapping("/api/v1")
public class OpenApiController {

    @Autowired
    private LoanReviewService reviewService;

    @ApiOperation(value = "대출 상태 단건 조회", notes = "신청 번호(ID)를 기반으로 대출의 현재 진행 상태를 조회합니다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "조회 성공"),
        @ApiResponse(code = 401, message = "인증 실패 (JWT 토큰 누락 또는 만료)"),
        @ApiResponse(code = 404, message = "존재하지 않는 신청 번호")
    })
    @GetMapping("/loan/{id}")
    public ResponseEntity<LoanApplication> getLoanStatus(
            @ApiParam(value = "대출 신청 번호", required = true, example = "APP-123456789")
            @PathVariable("id") String id) {
        
        LoanApplication app = reviewService.getApplicationById(id);
        
        if (app == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(app);
    }
}
package com.finance.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.finance.dto.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * API 요청(/api/**)인지 확인하는 메서드
     */
    private boolean isApiRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI != null && (requestURI.contains("/api/") || requestURI.contains("/LoanCore/api/"));
    }

    /**
     * 공통 응답 처리 (API면 JSON, 일반 요청이면 View 반환)
     */
    private Object handleResponse(int status, String message, String code, HttpServletRequest request, Model model, String viewName) {
        if (isApiRequest(request)) {
            ApiResponse<Void> response = ApiResponse.error(status, message, code);
            return new ResponseEntity<>(response, HttpStatus.valueOf(status));
        } else {
            model.addAttribute("errorMessage", message);
            model.addAttribute("errorCode", code);
            return "error/" + viewName; // 예: error/500.jsp 또는 error/400.jsp
        }
    }

    /**
     * 1. NullPointerException 처리 (E002)
     */
    @ExceptionHandler(NullPointerException.class)
    public Object handleNullPointerException(NullPointerException ex, HttpServletRequest request, Model model) {
        logger.error("Null Pointer Exception 발생: ", ex);
        return handleResponse(400, "잘못된 요청 파라미터 (Null값 참조)", "E002", request, model, "500");
    }

    /**
     * 2. 데이터 바인딩 유효성 검사 실패 (BindException) (E003)
     */
    @ExceptionHandler(BindException.class)
    public Object handleBindException(BindException ex, HttpServletRequest request, Model model) {
        logger.warn("데이터 유효성 검사 실패: {}", ex.getMessage());
        
        if (isApiRequest(request)) {
            return new ResponseEntity<>(ApiResponse.error(400, "잘못된 입력 데이터입니다.", "E003"), HttpStatus.BAD_REQUEST);
        } else {
            model.addAttribute("errors", ex.getAllErrors());
            return "apply"; // 신청 페이지로 돌려보냄
        }
    }

    /**
     * 3. 잘못된 인자 예외 (IllegalArgumentException) (E004)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request, Model model) {
        logger.error("부적절한 인자 전달: ", ex);
        return handleResponse(400, ex.getMessage(), "E004", request, model, "500");
    }

    /**
     * 4. 최상위 모든 예외 처리 (E001)
     */
    @ExceptionHandler(Exception.class)
    public Object handleAllExceptions(Exception ex, HttpServletRequest request, Model model) {
        logger.error("시스템 내부 오류 발생: ", ex);
        return handleResponse(500, "서버 내부 오류가 발생했습니다. 관리자에게 문의하세요.", "E001", request, model, "500");
    }
}
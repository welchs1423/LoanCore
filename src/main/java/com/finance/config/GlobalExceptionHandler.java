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

import com.finance.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // API 요청(/api/**)인지 확인하는 메서드
    private boolean isApiRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI != null && requestURI.contains("/api/");
    }

    // 일반적인 예외 처리
    @ExceptionHandler(Exception.class)
    public Object handleAllExceptions(Exception ex, HttpServletRequest request, Model model) {
        logger.error("시스템 내부 오류 발생: ", ex);

        if (isApiRequest(request)) {
            ErrorResponse response = new ErrorResponse(500, "서버 내부 오류", "E001");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            model.addAttribute("errorMessage", "예기치 않은 시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
            return "error/500"; 
        }
    }

    // NullPointerException 처리 추가
    @ExceptionHandler(NullPointerException.class)
    public Object handleNullPointerException(NullPointerException ex, HttpServletRequest request, Model model) {
        logger.error("Null Pointer Exception 발생: ", ex);

        if (isApiRequest(request)) {
            ErrorResponse response = new ErrorResponse(400, "잘못된 요청 파라미터 (Null값 참조)", "E002");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            model.addAttribute("errorMessage", "잘못된 요청 파라미터로 인한 오류가 발생했습니다.");
            return "error/500";
        }
    }

    // 데이터 바인딩 유효성 검사 실패 시 처리
    @ExceptionHandler(BindException.class)
    public Object handleBindException(BindException ex, HttpServletRequest request, Model model) {
        logger.warn("데이터 유효성 검사 실패: {}", ex.getMessage());
        
        if (isApiRequest(request)) {
            ErrorResponse response = new ErrorResponse(400, "잘못된 입력 데이터입니다.", "E003");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            model.addAttribute("errors", ex.getAllErrors());
            return "apply"; 
        }
    }
}
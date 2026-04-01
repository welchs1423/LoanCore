package com.finance.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 일반적인 예외 처리 (500 에러 페이지로)
    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        logger.error("시스템 내부 오류 발생: ", ex);
        model.addAttribute("errorMessage", "예기치 않은 시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
        return "error/500"; // views/error/500.jsp
    }

    // 데이터 바인딩/유효성 검사 실패 시 처리 (필요에 따라 JSON 응답으로 변경 가능)
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException ex, Model model) {
        logger.warn("데이터 유효성 검사 실패: {}", ex.getMessage());
        // 에러 정보를 모델에 담아 이전 페이지(또는 에러 페이지)로 돌려보냄
        model.addAttribute("errors", ex.getAllErrors());
        return "apply"; // 예시: 다시 입력 폼으로 (실제로는 상황에 맞게 라우팅)
    }
}
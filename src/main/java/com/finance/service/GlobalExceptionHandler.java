package com.finance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        logger.error("시스템 예외 발생: ", ex);
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
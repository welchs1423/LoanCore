package com.finance.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    public void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleAllExceptions() {
        Exception ex = new Exception("테스트 에러");
        Model model = new ExtendedModelMap();

        String viewName = exceptionHandler.handleAllExceptions(ex, model);

        assertEquals("error/500", viewName);
        assertTrue(model.containsAttribute("errorMessage"));
    }

    @Test
    public void testHandleBindException() {
        BindException ex = new BindException(new Object(), "testObject");
        Model model = new ExtendedModelMap();

        String viewName = exceptionHandler.handleBindException(ex, model);

        assertEquals("apply", viewName);
        assertTrue(model.containsAttribute("errors"));
    }
}
package com.finance.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
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
        MockHttpServletRequest request = new MockHttpServletRequest();
        Model model = new ExtendedModelMap();

        // 리턴 타입을 String으로 캐스팅
        String viewName = (String) exceptionHandler.handleAllExceptions(ex, request, model);

        assertEquals("error/500", viewName);
        assertTrue(model.containsAttribute("errorMessage"));
    }

    @Test
    public void testHandleBindException() {
        BindException ex = new BindException(new Object(), "testObject");
        MockHttpServletRequest request = new MockHttpServletRequest();
        Model model = new ExtendedModelMap();

        // 리턴 타입을 String으로 캐스팅
        String viewName = (String) exceptionHandler.handleBindException(ex, request, model);

        assertEquals("apply", viewName);
        assertTrue(model.containsAttribute("errors"));
    }
}
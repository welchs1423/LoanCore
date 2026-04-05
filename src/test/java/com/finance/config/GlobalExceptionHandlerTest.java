package com.finance.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ExtendedModelMap;
import com.finance.dto.ApiResponse;

public class GlobalExceptionHandlerTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testHandleApiError() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/loan/test");

        ResponseEntity<ApiResponse<Void>> response = (ResponseEntity<ApiResponse<Void>>) handler.handleNullPointerException(
                new NullPointerException(), request, new ExtendedModelMap());

        assertEquals(400, response.getBody().getStatus());
        assertEquals("E002", response.getBody().getCode());
    }

    @Test
    public void testHandleViewError() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/loan/apply");
        ExtendedModelMap model = new ExtendedModelMap();

        String viewName = (String) handler.handleAllExceptions(new Exception(), request, model);

        assertEquals("error/500", viewName);
        assertEquals("E001", model.get("errorCode"));
    }
}
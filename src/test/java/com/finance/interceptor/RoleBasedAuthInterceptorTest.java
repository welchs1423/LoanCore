package com.finance.interceptor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class RoleBasedAuthInterceptorTest {

    private RoleBasedAuthInterceptor interceptor;

    @BeforeEach
    public void setUp() {
        interceptor = new RoleBasedAuthInterceptor();
    }

    @Test
    public void testPreHandle_SuperAdmin() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.getSession().setAttribute("ADMIN_ROLE", "SUPER_ADMIN");

        assertTrue(interceptor.preHandle(request, response, null));
    }

    @Test
    public void testPreHandle_Manager() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.getSession().setAttribute("ADMIN_ROLE", "MANAGER");

        assertFalse(interceptor.preHandle(request, response, null));
        assertTrue(response.getStatus() == 403);
    }
}
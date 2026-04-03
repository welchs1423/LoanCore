package com.finance.interceptor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class IpWhitelistInterceptorTest {

    private IpWhitelistInterceptor interceptor;

    @BeforeEach
    public void setUp() {
        interceptor = new IpWhitelistInterceptor();
    }

    @Test
    public void testPreHandle_AllowedIp() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setRemoteAddr("192.168.1.100");

        assertTrue(interceptor.preHandle(request, response, null));
    }

    @Test
    public void testPreHandle_BlockedIp() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setRemoteAddr("203.0.113.1");

        assertFalse(interceptor.preHandle(request, response, null));
        assertTrue(response.getStatus() == 403);
    }
}
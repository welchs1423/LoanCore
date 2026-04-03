package com.finance.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class IpWhitelistInterceptor implements HandlerInterceptor {

    private final List<String> allowedIps = Arrays.asList("192.168.1.100", "10.0.0.5");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        
        if (!allowedIps.contains(clientIp)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Untrusted IP");
            return false;
        }
        return true;
    }
}
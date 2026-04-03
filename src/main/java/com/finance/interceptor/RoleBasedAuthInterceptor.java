package com.finance.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class RoleBasedAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("ADMIN_ROLE");
        
        if (role == null || !role.equals("SUPER_ADMIN")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return false;
        }
        return true;
    }
}
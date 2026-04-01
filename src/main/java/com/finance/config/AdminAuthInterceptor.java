package com.finance.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        
        // 세션에 "adminLogined" 정보가 없으면 로그인 페이지로 리다이렉트
        if (session.getAttribute("adminLogined") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        
        return true; // 로그인되어 있으면 요청 계속 진행
    }
}
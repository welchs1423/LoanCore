package com.finance.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        
        // 세션에 adminId가 없으면 로그인 페이지로 강제 튕겨냄
        if (session.getAttribute("adminId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false; // 컨트롤러 진입 차단
        }
        return true; // 통과
    }
}
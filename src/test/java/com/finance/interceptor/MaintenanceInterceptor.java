package com.finance.interceptor;

import java.time.LocalTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class MaintenanceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LocalTime now = LocalTime.now();
        // 23:50 ~ 00:10 사이 시스템 점검 시간 설정
        if (now.isAfter(LocalTime.of(23, 50)) || now.isBefore(LocalTime.of(0, 10))) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            response.getWriter().write("시스템 정기 점검 시간입니다. (23:50~00:10)");
            return false;
        }
        return true;
    }
}
package com.finance.interceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class RateLimitInterceptor implements HandlerInterceptor {
    
    private final Map<String, Long> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> timeWindows = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();
        
        timeWindows.putIfAbsent(clientIp, currentTime);
        requestCounts.putIfAbsent(clientIp, 0L);
        
        if (currentTime - timeWindows.get(clientIp) > 60000) {
            timeWindows.put(clientIp, currentTime);
            requestCounts.put(clientIp, 0L);
        }
        
        long currentCount = requestCounts.get(clientIp);
        if (currentCount >= 100) {
            response.setStatus(429);
            response.getWriter().write("Too Many Requests");
            return false;
        }
        
        requestCounts.put(clientIp, currentCount + 1);
        return true;
    }
}
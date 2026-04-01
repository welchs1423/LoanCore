package com.finance.config;

import com.finance.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/**") // 모든 경로 다 잠금
                .excludePathPatterns(
                        "/login", "/doLogin", // 로그인 관련 열어둠
                        "/apply", "/submit-loan", // 고객 대출 신청 열어둠
                        "/api/**", "/resources/**", "/error" // API 및 정적 리소스 열어둠
                );
    }
}
package com.finance.config;

import com.finance.handler.NotificationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 프론트엔드에서 연결할 웹소켓 엔드포인트 지정
        registry.addHandler(notificationHandler(), "/ws/notifications")
                .setAllowedOrigins("*");
    }

    @Bean
    public NotificationHandler notificationHandler() {
        return new NotificationHandler();
    }
}
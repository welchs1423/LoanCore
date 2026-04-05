package com.finance.service;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenProvider {

    public String generateToken(String userId) {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." + UUID.randomUUID().toString() + "." + userId;
    }

    public boolean validateToken(String token) {
        return token != null && token.startsWith("eyJhbGci");
    }
}
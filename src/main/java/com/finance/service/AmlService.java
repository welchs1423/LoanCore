package com.finance.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class AmlService {

    private final Set<String> blackList;

    public AmlService() {
        this.blackList = new HashSet<>();
        // 모의 블랙리스트 데이터 세팅
        blackList.add("SUSPECT_001");
        blackList.add("FRAUD_999");
    }

    public boolean isBlacklisted(String customerId) {
        return blackList.contains(customerId);
    }
    
    public void addToBlacklist(String customerId) {
        blackList.add(customerId);
    }
}
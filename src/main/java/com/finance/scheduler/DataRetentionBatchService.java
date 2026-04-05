package com.finance.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DataRetentionBatchService {

    @Scheduled(cron = "0 0 3 * * ?")
    public void purgeExpiredData() {
        
    }
}
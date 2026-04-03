package com.finance.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.finance.mapper.LoanMapper;

@Service
public class DataRetentionBatchService {

    private final LoanMapper loanMapper;

    public DataRetentionBatchService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void purgeExpiredData() {
        // 법정 보유 기간이 지난 대출 데이터 삭제 로직
    }
}
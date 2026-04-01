package com.finance.domain;

import lombok.Data;

@Data
public class AuditLog {
    private String action;
    private String target;
    private String actor;

    public AuditLog(String action, String target, String actor) {
        this.action = action;
        this.target = target;
        this.actor = actor;
    }
}
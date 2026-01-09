package com.src.job_queue_system.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPayload {
    private String to;
    private String subject;
    private String body;

    public JobPayload(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
}

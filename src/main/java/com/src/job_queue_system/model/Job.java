package com.src.job_queue_system.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Job {
    private String jobId;
    private String type;
    private JobPayload payload;
    private JobStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String result;
    private int retryCount;
    private int maxRetries;

    public Job(String jobId, String type, JobPayload payload) {
        this.jobId = jobId;
        this.type = type;
        this.payload = payload;
        this.status = JobStatus.QUEUED;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.retryCount = 0;
        this.maxRetries = 5;
    }

    public void incrementRetry() {
        this.retryCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean canRetry() {
        return retryCount < maxRetries;
    }

}

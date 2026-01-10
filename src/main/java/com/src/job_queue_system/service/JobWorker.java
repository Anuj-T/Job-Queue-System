package com.src.job_queue_system.service;

import com.src.job_queue_system.model.Job;
import com.src.job_queue_system.model.JobStatus;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JobWorker {
    private final JobQueueService jobQueueService;

    private JobWorker(JobQueueService jobQueueService) {
        this.jobQueueService = jobQueueService;
    }

    @PostConstruct
    public void startWorker() {
        Thread worker = new Thread(() -> {
            while (true) {
                try {
                    Job job = jobQueueService.pollJob();
                    processJob(job);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        worker.setDaemon(true);
        worker.start();
    }

    private void processJob(Job job) {
        job.setStatus(JobStatus.IN_PROGRESS);
        try {
            Thread.sleep(3000);
            job.setStatus(JobStatus.COMPLETED);
            job.setResult("Job processed successfully");
            job.setUpdatedAt(LocalDateTime.now());
        } catch (Exception e) {
            job.incrementRetry();
            if (job.canRetry()) {
                job.setStatus(JobStatus.QUEUED);
                jobQueueService.resubmitJob(job);
                job.setResult("Job Failed,retrying attempt : " + job.getRetryCount());
            } else {
                job.setStatus(JobStatus.FAILED);
                job.setResult("Job processing failed");
                job.setUpdatedAt(LocalDateTime.now());
            }
        }
    }
}

package com.src.job_queue_system.service;

import com.src.job_queue_system.model.Job;
import com.src.job_queue_system.model.JobStatus;
import org.springframework.stereotype.Service;

@Service
public class JobWorker {
    private final JobQueueService jobQueueService;

    private JobWorker(JobQueueService jobQueueService) {
        this.jobQueueService = jobQueueService;
    }

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
        } catch (Exception e) {
            job.setStatus(JobStatus.FAILED);
        }
    }
}

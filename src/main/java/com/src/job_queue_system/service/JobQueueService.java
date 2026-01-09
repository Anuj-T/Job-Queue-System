package com.src.job_queue_system.service;

import com.src.job_queue_system.model.Job;
import com.src.job_queue_system.model.JobPayload;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobQueueService {
    private final Map<String, Job> jobs = new HashMap<>();
    private final Queue<Job> queue = new LinkedList<>();

    public Job submitJob(String type, JobPayload jobPayload) {
        String jobId = UUID.randomUUID().toString();
        Job job = new Job(jobId, type, jobPayload);
        jobs.put(job.getJobId(), job);
        queue.offer(job);
        return job;
    }

    public Job pollJob() {
        return queue.poll();
    }

}
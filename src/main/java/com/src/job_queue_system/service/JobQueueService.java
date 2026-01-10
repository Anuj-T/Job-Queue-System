package com.src.job_queue_system.service;

import com.src.job_queue_system.model.Job;
import com.src.job_queue_system.model.JobPayload;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class JobQueueService {
    private final Map<String, Job> jobs = new ConcurrentHashMap<>();
    private final BlockingQueue<Job> queue = new LinkedBlockingQueue<Job>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Job submitJob(String type, Map<String, Object> payloadObj) {
        String jobId = UUID.randomUUID().toString();
        JobPayload jobPayload = objectMapper.convertValue(payloadObj, JobPayload.class);
        Job job = new Job(jobId, type, jobPayload);
        jobs.put(job.getJobId(), job);
        queue.offer(job);
        return job;
    }

    public Job pollJob() throws InterruptedException {
        return queue.take();
    }

    public Job getJob(String jobId) {
        return jobs.get(jobId);
    }

    public List<Job> getAllJobs() {
        return new ArrayList<>(jobs.values());
    }
}
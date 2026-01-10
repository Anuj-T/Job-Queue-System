package com.src.job_queue_system.controller;

import com.src.job_queue_system.model.Job;
import com.src.job_queue_system.service.JobQueueService;
import com.src.job_queue_system.validator.JobValidator;
import com.src.job_queue_system.validator.PayloadValidator;
import com.src.job_queue_system.validator.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {
    private final JobQueueService queueService;
    private final JobValidator jobValidator;
    private final PayloadValidator payloadValidator;

    public JobController(JobQueueService queueService, JobValidator jobValidator, PayloadValidator payloadValidator) {
        this.queueService = queueService;
        this.jobValidator = jobValidator;
        this.payloadValidator = payloadValidator;
    }

    @PostMapping
    public ResponseEntity<?> submitJob(@RequestBody Map<String, Object> request) {
        try {
            String type = request.get("type").toString();
            Object payloadObj = request.get("payload");

            jobValidator.validateJobSubmission(type, payloadObj);

            Map<String, Object> payload = (Map<String, Object>) payloadObj;
            if("email".equalsIgnoreCase(type)) {
                payloadValidator.validateEmailPayload(payload);
            }

            Job job = queueService.submitJob(type, payload);
            return ResponseEntity.ok(job);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = queueService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{jobId}")
    public Job getJob(@PathVariable String jobId) {
        return queueService.getJob(jobId);
    }
}
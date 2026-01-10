package com.src.job_queue_system.controller;

import com.src.job_queue_system.model.Job;
import com.src.job_queue_system.model.JobPayload;
import com.src.job_queue_system.service.JobQueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {
    private final JobQueueService queueService;
    private final ObjectMapper objectMapper;

    public JobController(JobQueueService queueService, ObjectMapper objectMapper) {
        this.queueService = queueService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<?> submitJob(@RequestBody Map<String, Object> request) {
        String type = request.get("type").toString();
        Object payloadObj = request.get("payload");

        if (type.isBlank()) {
            return ResponseEntity.badRequest().body("error"); // TODO : Add validation here
        }
        if (payloadObj == null) {
            return ResponseEntity.badRequest().body("error");
        }

        JobPayload payload = objectMapper.convertValue(payloadObj, JobPayload.class);
        Job job = queueService.submitJob(type, payload);
        return ResponseEntity.ok(job);
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = queueService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }
}
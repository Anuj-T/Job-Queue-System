package com.src.job_queue_system.validator;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JobValidator {

    public void validateJobSubmission(String type, Object payload) {
        if (type == null || type.trim().isEmpty()) {
            throw new ValidationException("Job type is required");
        }
        if(!(payload instanceof Map<?, ?> payloadMap)) {
            throw new ValidationException("Payload must be JSON");
        }

        if(payloadMap.isEmpty()) {
            throw new ValidationException("Payload cannot be empty");
        }

    }
}

package com.src.job_queue_system.validator;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}

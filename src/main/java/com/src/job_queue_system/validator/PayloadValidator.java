package com.src.job_queue_system.validator;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PayloadValidator {

    public void validateEmailPayload(Map<String, Object> payload) {
        String to = (String) payload.get("to");
        String subject = (String) payload.get("subject");
        String body = (String) payload.get("body");

        if (to == null || to.trim().isBlank()) {
            throw new ValidationException("Email 'to' field is required");
        }

        if (!isValidEmail(to)) {
            throw new ValidationException("Invalid email format");
        }

        if (subject == null || subject.trim().isBlank()) {
            throw new ValidationException("Email 'subject' is required");
        }

        if (body == null || body.trim().isBlank()) {
            throw new ValidationException("Email 'body' is required");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}

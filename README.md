# Loom Link
https://www.loom.com/share/6556efafead540789a3c9f88ed193a78

# Job Queue System

A Spring Boot application for managing and processing jobs asynchronously with multiple worker threads.

## Features

- Submit jobs via REST API or web UI
- 3 parallel worker threads for concurrent job processing
- Automatic retry mechanism (up to 5 retries)
- Real-time job status tracking
- Auto-refresh job list every 5 seconds

## Tech Stack

- Java 17
- Spring Boot 4.0.1
- Maven
- Docker

## Running Locally

### With Maven
```bash
./mvnw spring-boot:run
```

### With Docker
```bash
docker build -t job-queue-system .
docker run -p 8080:8080 job-queue-system
```

Access the application at `http://localhost:8080`

## API Endpoints

- `POST /api/jobs` - Submit a new job
- `GET /api/jobs` - Get all jobs
- `GET /api/jobs/{jobId}` - Get specific job with jobId

### Example Request
```json
POST /api/jobs
{
  "type": "email",
  "payload": {
    "to": "user@example.com",
    "subject": "Hello",
    "body": "Test email"
  }
}
```

## Job Status

- `QUEUED` - Job submitted and waiting
- `IN_PROGRESS` - Job being processed
- `COMPLETED` - Job finished successfully
- `FAILED` - Job failed after max retries

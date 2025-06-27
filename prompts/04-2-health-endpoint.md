# Prompt 4.2: Health Endpoint Implementation

```
Complete the HealthController by implementing the health check endpoint:

Requirements for the existing HealthController class:
- Add @GetMapping("/health") method
- Method name: getHealth
- Return type: ResponseEntity<HealthResponse>
- Call healthService.getHealthStatus() to get the response
- Return ResponseEntity.ok() with the HealthResponse
- Ensure the endpoint responds to GET /api/health

The endpoint should:
- Return HTTP 200 status
- Return JSON response with status "UP" and current timestamp
- Use the injected HealthService to get the response data

Import ResponseEntity and GetMapping annotations as needed.
```
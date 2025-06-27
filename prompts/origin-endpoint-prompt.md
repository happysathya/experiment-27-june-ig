# Add Origin Endpoint to Spring Boot Application

I want to add a GET origin endpoint to my Spring Boot application. The endpoint should return the IP address from an external service.

## Requirements

- **Endpoint path**: `/api/origin`
- **External service**: Call `GET https://httpbin.org/get`
- **Response format**: Extract the `origin` field and return as `{"origin": "213.119.12.224"}`
- **Error handling**: Return 500 status with error response on failures
- **Caching**: No caching - fresh call each time
- **HTTP client**: Use default Java HttpClient

## Expected External Service Response
```json
{
  "args": {},
  "headers": { ... },
  "origin": "213.119.12.224",
  "url": "https://httpbin.org/get"
}
```

## Implementation Requirements

1. Follow Spring Boot layered architecture (Controller → Service → DTO)
2. Create a service interface and implementation
3. Use Java records for DTOs
4. Add proper error handling with global exception handler
5. Include comprehensive unit and integration tests
6. Use constructor-based dependency injection
7. Add the endpoint to existing controller if appropriate

## Testing Requirements

- Unit tests for service logic
- Integration tests for controller endpoints
- Mock external dependencies in tests
- Ensure all existing tests continue to pass
- Manual testing of the endpoint

The implementation should follow existing code conventions and patterns in the codebase.
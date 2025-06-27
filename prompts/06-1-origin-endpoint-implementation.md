# Origin Endpoint Implementation Pattern

## Overview
This prompt documents the implementation of a new REST endpoint that calls an external HTTP service and returns structured JSON responses. It follows the established Spring Boot layered architecture pattern.

## User Request
```
I want to add a get origin endpoint. The endpoint should return the ip address returned from the below endpoint
GET "https://httpbin.org/get"

The response body from the endpoint:
{
  "args": {},
  "headers": { ... },
  "origin": "213.119.12.224",
  "url": "https://httpbin.org/get"
}

Requirements clarifications:
1. Endpoint path: /api/origin
2. Response format: Wrapped JSON like {"origin": "213.119.12.224"}
3. Error handling: Return 500 status with error response on failures
4. Caching: No caching - fresh call each time
5. HTTP client: Use default Java HttpClient
```

## Implementation Pattern

### 1. Create Response DTO
```java
// src/main/java/com/ig/dto/OriginResponse.java
package com.ig.dto;

public record OriginResponse(String origin) {
}
```

### 2. Create Service Interface
```java
// src/main/java/com/ig/service/OriginService.java
package com.ig.service;

import com.ig.dto.OriginResponse;

public interface OriginService {
    OriginResponse getOrigin();
}
```

### 3. Create Service Implementation
```java
// src/main/java/com/ig/service/OriginServiceImpl.java
package com.ig.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ig.dto.OriginResponse;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OriginServiceImpl implements OriginService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OriginServiceImpl() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public OriginResponse getOrigin() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://httpbin.org/get"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to fetch origin: HTTP " + response.statusCode());
            }

            JsonNode jsonNode = objectMapper.readTree(response.body());
            String origin = jsonNode.get("origin").asText();

            return new OriginResponse(origin);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch origin from external service", e);
        }
    }
}
```

### 4. Add Endpoint to Existing Controller
```java
// Update src/main/java/com/ig/controller/HealthController.java
// Add imports:
import com.ig.dto.OriginResponse;
import com.ig.service.OriginService;

// Add to constructor:
private final OriginService originService;

public HealthController(HealthService healthService, OriginService originService) {
    this.healthService = healthService;
    this.originService = originService;
}

// Add endpoint method:
@GetMapping("/origin")
public ResponseEntity<OriginResponse> getOrigin() {
    OriginResponse originResponse = originService.getOrigin();
    return ResponseEntity.ok(originResponse);
}
```

### 5. Add Global Exception Handler
```java
// src/main/java/com/ig/controller/ControllerExceptionHandler.java
package com.ig.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal server error", "message", e.getMessage()));
    }
}
```

### 6. Service Unit Tests
```java
// src/test/java/com/ig/service/OriginServiceImplTest.java
package com.ig.service;

import com.ig.dto.OriginResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OriginServiceImplTest {

    private final OriginService originService = new OriginServiceImpl();

    @Test
    void getOrigin_ShouldReturnOriginResponse() {
        OriginResponse response = originService.getOrigin();
        
        assertNotNull(response);
        assertNotNull(response.origin());
        assertFalse(response.origin().isEmpty());
    }

    @Test
    void getOrigin_ShouldReturnValidIpAddress() {
        OriginResponse response = originService.getOrigin();
        
        String origin = response.origin();
        assertTrue(origin.contains("."), "Origin should contain dots indicating IP address format");
        assertNotNull(origin);
        assertFalse(origin.isEmpty());
    }
}
```

### 7. Controller Integration Tests
```java
// src/test/java/com/ig/controller/OriginControllerTest.java
package com.ig.controller;

import com.ig.dto.OriginResponse;
import com.ig.service.HealthService;
import com.ig.service.OriginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HealthController.class)
class OriginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HealthService healthService;

    @MockBean
    private OriginService originService;

    @Test
    void getOrigin_ShouldReturnHttp200() throws Exception {
        OriginResponse mockResponse = new OriginResponse("192.168.1.1");
        when(originService.getOrigin()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/origin"))
                .andExpect(status().isOk());
    }

    @Test
    void getOrigin_ShouldReturnApplicationJson() throws Exception {
        OriginResponse mockResponse = new OriginResponse("192.168.1.1");
        when(originService.getOrigin()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/origin"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getOrigin_ShouldReturnOriginField() throws Exception {
        OriginResponse mockResponse = new OriginResponse("192.168.1.1");
        when(originService.getOrigin()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/origin"))
                .andExpect(jsonPath("$.origin").value("192.168.1.1"));
    }

    @Test
    void getOrigin_ShouldReturn500WhenServiceThrowsException() throws Exception {
        when(originService.getOrigin()).thenThrow(new RuntimeException("External service error"));

        mockMvc.perform(get("/api/origin"))
                .andExpect(status().isInternalServerError());
    }
}
```

### 8. Update Existing Tests
```java
// Update src/test/java/com/ig/controller/HealthControllerTest.java
// Add import:
import com.ig.service.OriginService;

// Add mock bean:
@MockBean
private OriginService originService;
```

## Key Patterns Followed

### 1. Layered Architecture
- **Controller Layer**: Handles HTTP requests/responses
- **Service Layer**: Contains business logic and external API calls
- **DTO Layer**: Data transfer objects using Java records

### 2. Dependency Injection
- Constructor-based dependency injection
- Interface-based service design
- @Service annotation for service implementations

### 3. Error Handling
- Global exception handler using @RestControllerAdvice
- RuntimeException for service layer errors
- Proper HTTP status codes (500 for internal errors)

### 4. Testing Strategy
- Unit tests for service logic
- Integration tests for controller endpoints
- Mock external dependencies in tests
- @WebMvcTest for controller testing

### 5. HTTP Client Usage
- Java HttpClient (no additional dependencies)
- JSON parsing with Jackson ObjectMapper
- Proper request headers and error handling

### 6. Code Conventions
- Package structure: controller, service, dto
- Naming conventions: Service interface + Impl
- Java records for immutable DTOs
- RESTful endpoint paths (/api/resource)

## Implementation Checklist

When implementing a new external API endpoint:

1. ✅ Create response DTO as Java record
2. ✅ Create service interface and implementation
3. ✅ Add endpoint to existing controller (or create new one)
4. ✅ Implement HTTP client call with proper error handling
5. ✅ Add global exception handler if not exists
6. ✅ Write comprehensive unit tests for service
7. ✅ Write integration tests for controller
8. ✅ Update existing test mocks if needed
9. ✅ Run all tests to ensure no regressions
10. ✅ Manual testing of endpoint functionality

## Testing Commands
```bash
# Run all tests
mvn test

# Start application
mvn spring-boot:run

# Test endpoints
curl http://localhost:8080/api/origin
curl http://localhost:8080/api/health
```

## Expected Results
- All tests pass (14/14 in this case)
- Endpoint returns proper JSON structure
- Error scenarios return 500 status
- No regressions in existing functionality

This pattern can be replicated for any new external API integration following the same layered architecture and testing approach.
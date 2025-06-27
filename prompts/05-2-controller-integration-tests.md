# Prompt 5.2: Controller Integration Tests

```
Create integration tests for the HealthController:

File: src/test/java/com/ig/controller/HealthControllerTest.java

Requirements:
- Package: com.ig.controller
- Class name: HealthControllerTest
- Use @WebMvcTest(HealthController.class) for focused web layer testing
- Mock the HealthService using @MockBean
- Inject MockMvc for testing HTTP endpoints

Test cases:
1. Test GET /api/health returns HTTP 200
2. Test response content type is application/json
3. Test response contains status "UP"
4. Test response contains timestamp field

Use MockMvc to perform requests and verify:
- HTTP status is 200
- Content type is JSON
- JSON response structure matches expected format
- Mock the HealthService.getHealthStatus() to return predictable test data

Import necessary Spring Boot test annotations, MockMvc, and testing utilities.
```
# Client Service Implementation Prompts

This directory contains step-by-step prompts for implementing the Client Service Health Check application.

## Execution Order

### Phase 1: Foundation (Prompts 01-*)
1. **01-1-project-structure.md** - Create Maven directory structure
2. **01-2-maven-config.md** - Configure pom.xml with dependencies
3. **01-3-spring-boot-app.md** - Create main application class

**Validation**: Run `mvn spring-boot:run` to verify application starts

### Phase 2: Data Contract (Prompts 02-*)
4. **02-1-health-response-record.md** - Create HealthResponse record

**Validation**: Verify JSON serialization works

### Phase 3: Service Layer (Prompts 03-*)
5. **03-1-service-interface.md** - Define HealthService interface
6. **03-2-service-implementation.md** - Implement HealthServiceImpl

**Validation**: Test service returns proper HealthResponse

### Phase 4: Controller Layer (Prompts 04-*)
7. **04-1-controller-setup.md** - Create HealthController structure
8. **04-2-health-endpoint.md** - Implement /api/health endpoint

**Validation**: Test GET /api/health returns 200 with correct JSON

### Phase 5: Testing (Prompts 05-*)
9. **05-1-service-unit-tests.md** - Create service unit tests
10. **05-2-controller-integration-tests.md** - Create controller integration tests

**Final Validation**: Run `mvn test` to ensure all tests pass

## Integration Points

- After Phase 1: Application starts without errors
- After Phase 2: JSON serialization works correctly
- After Phase 3: Service layer is functional and testable
- After Phase 4: Full REST endpoint is working
- After Phase 5: Comprehensive test coverage is in place

## Architecture

The implementation follows hexagonal architecture principles:
- **Controller Layer**: Handles HTTP requests/responses
- **Service Layer**: Contains business logic
- **DTO Layer**: Data transfer objects (Java records)

Each prompt builds incrementally with no orphaned code, ensuring a fully integrated Spring Boot application.
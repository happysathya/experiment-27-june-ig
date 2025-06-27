# Project Specification: Client Service Health Check

## Technical Stack
- **Java Version**: 21
- **Framework**: Spring Boot 3.2.x (latest stable)
- **Build Tool**: Maven
- **Architecture**: Hexagonal Architecture with clear separation of concerns

## Maven Configuration
- **Group ID**: `com.ig`
- **Artifact ID**: `client-service`
- **Package Structure**: `com.ig`

## Architecture Layers
1. **Controller Layer**: REST endpoint handling
2. **Service Layer**: Business logic (simple status return with timestamp)
3. **Repository Layer**: Data access (not needed for this simple use case, but structure should support it)

## Health Check Endpoint Specification
- **Path**: `/api/health`
- **HTTP Method**: GET
- **Response Format**: JSON
- **Success Response**:
  ```json
  {
    "status": "UP",
    "timestamp": "2024-01-01T12:00:00Z"
  }
  ```
- **HTTP Status Code**: 200 (OK)

## Implementation Requirements
- Use Java Records for response DTOs
- Service layer returns hardcoded "UP" status with current timestamp
- Follow hexagonal architecture principles
- Include proper Maven dependencies for Spring Boot 3.2.x and Java 21

## Directory Structure
```
client-service/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── ig/
│                   ├── ClientServiceApplication.java
│                   ├── controller/
│                   │   └── HealthController.java
│                   ├── service/
│                   │   └── HealthService.java
│                   └── dto/
│                       └── HealthResponse.java
├── pom.xml
└── README.md
```

## Dependencies Required
- spring-boot-starter-web
- spring-boot-starter-test (for testing)
- Maven compiler plugin configured for Java 21
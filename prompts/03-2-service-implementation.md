# Prompt 3.2: Service Implementation

```
Create the service implementation with the following requirements:

File: src/main/java/com/ig/service/HealthServiceImpl.java

Requirements:
- Package: com.ig.service
- Class name: HealthServiceImpl
- Implement HealthService interface
- Add @Service annotation for Spring dependency injection
- Implement getHealthStatus() method to:
  - Return hardcoded status "UP"
  - Generate current timestamp in ISO 8601 format (use java.time.Instant)
  - Return new HealthResponse with status and timestamp

Import necessary classes:
- HealthService interface
- HealthResponse record
- Spring @Service annotation
- Java time classes
```
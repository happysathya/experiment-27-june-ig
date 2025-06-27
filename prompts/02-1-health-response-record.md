# Prompt 2.1: Health Response Record

```
Create a Java record for the health check response with the following specifications:

File: src/main/java/com/ig/dto/HealthResponse.java

Requirements:
- Use Java 21 record syntax
- Package: com.ig.dto
- Record name: HealthResponse
- Fields:
  - status (String)
  - timestamp (String in ISO 8601 format)
- Ensure the record can be properly serialized to JSON by Spring Boot's default Jackson configuration

Example expected JSON output:
{
  "status": "UP",
  "timestamp": "2024-01-01T12:00:00Z"
}
```
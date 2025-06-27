# Prompt 5.1: Service Unit Tests

```
Create unit tests for the HealthServiceImpl:

File: src/test/java/com/ig/service/HealthServiceImplTest.java

Requirements:
- Package: com.ig.service
- Class name: HealthServiceImplTest
- Use JUnit 5 (@Test annotation)
- Test the getHealthStatus() method

Test cases:
1. Test that getHealthStatus() returns HealthResponse with status "UP"
2. Test that getHealthStatus() returns HealthResponse with non-null timestamp
3. Test that timestamp is in valid ISO 8601 format
4. Test that multiple calls return different timestamps (within reasonable time)

Use assertions to verify:
- Status equals "UP"
- Timestamp is not null
- Timestamp format validation
- Use java.time.Instant.parse() to validate timestamp format

Import necessary testing classes and the classes under test.
```
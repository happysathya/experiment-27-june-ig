package com.ig.service;

import com.ig.dto.HealthResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class HealthServiceImplTest {

    private final HealthServiceImpl healthService = new HealthServiceImpl();

    @Test
    void getHealthStatus_ShouldReturnStatusUp() {
        HealthResponse response = healthService.getHealthStatus();
        
        assertEquals("UP", response.status());
    }

    @Test
    void getHealthStatus_ShouldReturnNonNullTimestamp() {
        HealthResponse response = healthService.getHealthStatus();
        
        assertNotNull(response.timestamp());
    }

    @Test
    void getHealthStatus_ShouldReturnValidIso8601Timestamp() {
        HealthResponse response = healthService.getHealthStatus();
        
        assertDoesNotThrow(() -> Instant.parse(response.timestamp()));
    }

    @Test
    void getHealthStatus_ShouldReturnDifferentTimestampsOnMultipleCalls() throws InterruptedException {
        HealthResponse response1 = healthService.getHealthStatus();
        Thread.sleep(1); // Ensure different timestamps
        HealthResponse response2 = healthService.getHealthStatus();
        
        assertNotEquals(response1.timestamp(), response2.timestamp());
    }
}
package com.ig.service;

import com.ig.dto.HealthResponse;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class HealthServiceImpl implements HealthService {

    @Override
    public HealthResponse getHealthStatus() {
        String currentTimestamp = Instant.now().toString();
        return new HealthResponse("UP", currentTimestamp);
    }
}
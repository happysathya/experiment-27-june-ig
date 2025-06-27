package com.ig.controller;

import com.ig.dto.HealthResponse;
import com.ig.dto.OriginResponse;
import com.ig.service.HealthService;
import com.ig.service.OriginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    private final HealthService healthService;
    private final OriginService originService;

    public HealthController(HealthService healthService, OriginService originService) {
        this.healthService = healthService;
        this.originService = originService;
    }

    @GetMapping("/health")
    public ResponseEntity<HealthResponse> getHealth() {
        HealthResponse healthResponse = healthService.getHealthStatus();
        return ResponseEntity.ok(healthResponse);
    }

    @GetMapping("/origin")
    public ResponseEntity<OriginResponse> getOrigin() {
        OriginResponse originResponse = originService.getOrigin();
        return ResponseEntity.ok(originResponse);
    }
}
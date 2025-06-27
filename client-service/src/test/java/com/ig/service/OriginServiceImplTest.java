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
        // Basic IP address validation - should contain dots
        assertTrue(origin.contains("."), "Origin should contain dots indicating IP address format");
        
        // Should not be empty or null
        assertNotNull(origin);
        assertFalse(origin.isEmpty());
    }
}
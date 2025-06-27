package com.ig.controller;

import com.ig.dto.HealthResponse;
import com.ig.service.HealthService;
import com.ig.service.OriginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HealthController.class)
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HealthService healthService;

    @MockBean
    private OriginService originService;

    @Test
    void getHealth_ShouldReturnHttp200() throws Exception {
        HealthResponse mockResponse = new HealthResponse("UP", "2024-01-01T12:00:00Z");
        when(healthService.getHealthStatus()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk());
    }

    @Test
    void getHealth_ShouldReturnApplicationJson() throws Exception {
        HealthResponse mockResponse = new HealthResponse("UP", "2024-01-01T12:00:00Z");
        when(healthService.getHealthStatus()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/health"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getHealth_ShouldReturnStatusUp() throws Exception {
        HealthResponse mockResponse = new HealthResponse("UP", "2024-01-01T12:00:00Z");
        when(healthService.getHealthStatus()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/health"))
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void getHealth_ShouldReturnTimestampField() throws Exception {
        HealthResponse mockResponse = new HealthResponse("UP", "2024-01-01T12:00:00Z");
        when(healthService.getHealthStatus()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/health"))
                .andExpect(jsonPath("$.timestamp").value("2024-01-01T12:00:00Z"));
    }
}
package com.ig.controller;

import com.ig.dto.OriginResponse;
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
class OriginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HealthService healthService;

    @MockBean
    private OriginService originService;

    @Test
    void getOrigin_ShouldReturnHttp200() throws Exception {
        OriginResponse mockResponse = new OriginResponse("192.168.1.1");
        when(originService.getOrigin()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/origin"))
                .andExpect(status().isOk());
    }

    @Test
    void getOrigin_ShouldReturnApplicationJson() throws Exception {
        OriginResponse mockResponse = new OriginResponse("192.168.1.1");
        when(originService.getOrigin()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/origin"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getOrigin_ShouldReturnOriginField() throws Exception {
        OriginResponse mockResponse = new OriginResponse("192.168.1.1");
        when(originService.getOrigin()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/origin"))
                .andExpect(jsonPath("$.origin").value("192.168.1.1"));
    }

    @Test
    void getOrigin_ShouldReturn500WhenServiceThrowsException() throws Exception {
        when(originService.getOrigin()).thenThrow(new RuntimeException("External service error"));

        mockMvc.perform(get("/api/origin"))
                .andExpect(status().isInternalServerError());
    }
}
package com.ig.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ig.dto.OriginResponse;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OriginServiceImpl implements OriginService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OriginServiceImpl() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public OriginResponse getOrigin() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://httpbin.org/get"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to fetch origin: HTTP " + response.statusCode());
            }

            JsonNode jsonNode = objectMapper.readTree(response.body());
            String origin = jsonNode.get("origin").asText();

            return new OriginResponse(origin);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch origin from external service", e);
        }
    }
}
package com.example.orchestrator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

@Component
public class JsonUtil {

    private final ObjectMapper objectMapper;

    public JsonUtil() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> T parseJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid JSON format", e);
        }
    }

    public String toJson(Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert map to JSON", e);
        }
    }

    public String getValueByKey(String json, String key) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode valueNode = rootNode.path(key);
            if (valueNode.isMissingNode()) {
                throw new RuntimeException("Key not found in JSON: " + key);
            }
            return valueNode.asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid JSON format", e);
        }
    }

    public Map<String, Object> getMapByKey(String json, String key) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode valueNode = rootNode.path(key);
            if (valueNode.isMissingNode()) {
                throw new RuntimeException("Key not found in JSON: " + key);
            }
            return objectMapper.convertValue(valueNode, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid JSON format", e);
        }
    }
}

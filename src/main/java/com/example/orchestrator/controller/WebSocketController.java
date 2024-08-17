package com.example.orchestrator.controller;

import com.example.orchestrator.constants.TopicConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.example.grpc.*;
import com.example.orchestrator.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.function.Function;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import com.example.orchestrator.service.KafkaProducerService;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final JsonUtil jsonUtil;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate, JsonUtil jsonUtil, KafkaProducerService kafkaProducerService) {
        this.messagingTemplate = messagingTemplate;
        this.jsonUtil = jsonUtil;
        this.kafkaProducerService = kafkaProducerService;
    }

    @MessageMapping("/initialData/{marketName}/{code}/{uuid}")
    public void handleInitialDataRequest(
            @DestinationVariable String marketName,
            @DestinationVariable String code,
            @DestinationVariable String uuid,
            @Payload Map<String, String> request
    ) {
        String timeframe = request.get("timeframe");
        kafkaProducerService.sendInitialDataRequest(marketName, code, timeframe, uuid);
    }

    public void sendActivityUpdate(List<Long> followerIds, Function<Long, Response> getLatestActivityForFollowees) {
        for (Long followerId : followerIds) {
            Response response = getLatestActivityForFollowees.apply(followerId);
            String result = response.getResult();
            Map<String, Object> latestActivityMap = jsonUtil.getMapByKey(result, "results");
            boolean success = Boolean.parseBoolean(latestActivityMap.get("success").toString());
            if (success) {
                this.messagingTemplate.convertAndSend(TopicConstants.TOPIC_ACTIVITIES_PREFIX + followerId, latestActivityMap);
            }
        }
    }

    public void sendLogoutMessage(Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("success", "true");
        this.messagingTemplate.convertAndSend(TopicConstants.TOPIC_LOGOUT_PREFIX + userId, response);
    }
}
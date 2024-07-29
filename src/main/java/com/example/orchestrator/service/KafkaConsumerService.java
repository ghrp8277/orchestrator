package com.example.orchestrator.service;

import com.example.orchestrator.constants.KafkaConstants;
import com.example.orchestrator.constants.TopicConstants;
import com.example.orchestrator.dto.InitialStockDto;
import com.example.orchestrator.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Map;

@Service
public class KafkaConsumerService {
    @Autowired
    private JsonUtil jsonUtil;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = KafkaConstants.INITIAL_DATA_TOPIC, groupId = KafkaConstants.INITIAL_DATA_GROUP_ID)
    public void consumeInitialDataRequest(String message) {
        InitialStockDto initialStockData = jsonUtil.parseJson(message, InitialStockDto.class);
        messagingTemplate.convertAndSend(TopicConstants.TOPIC_INITIAL_DATA_PREFIX, initialStockData);
    }

    @KafkaListener(topics = KafkaConstants.ERROR_TOPIC, groupId = KafkaConstants.INITIAL_DATA_GROUP_ID)
    public void consumeErrorRequest(String message) {
        Map<String, String> errorData = jsonUtil.parseJson(message, Map.class);
        messagingTemplate.convertAndSend(TopicConstants.TOPIC_ERROR_PREFIX, errorData);
    }
}

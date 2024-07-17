package com.example.orchestrator.controller;

import com.example.orchestrator.constants.TopicConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.example.grpc.*;
import com.example.orchestrator.util.JsonUtil;
import java.util.Map;
import java.util.List;
import java.util.function.Function;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final JsonUtil jsonUtil;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate, JsonUtil jsonUtil) {
        this.messagingTemplate = messagingTemplate;
        this.jsonUtil = jsonUtil;
    }

    public void sendActivityUpdate(List<Long> followerIds, Function<Long, Response> getLatestActivityForFollowees) {
        for (Long followerId : followerIds) {
            Response response = getLatestActivityForFollowees.apply(followerId);
            String result = response.getResult();

            if (!result.isEmpty()) {
                Map<String, Object> latestActivityMap = jsonUtil.getMapByKey(result, "latest_activity");
                if (latestActivityMap != null && !latestActivityMap.isEmpty()) {
                    String message = latestActivityMap.get("message").toString();
                    if (!message.isEmpty()) {
                        this.messagingTemplate.convertAndSend(TopicConstants.TOPIC_ACTIVITIES_PREFIX + followerId, message);
                    }
                }
            }
        }
    }

    public void sendLogoutMessage(Long userId) {
        this.messagingTemplate.convertAndSend(TopicConstants.TOPIC_LOGOUT_PREFIX + userId, "logout");
    }
}
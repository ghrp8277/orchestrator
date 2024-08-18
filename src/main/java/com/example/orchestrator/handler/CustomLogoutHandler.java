package com.example.orchestrator.handler;

import com.example.orchestrator.constants.TokenConstants;
import com.example.orchestrator.controller.WebSocketController;
import com.example.orchestrator.dto.request.auth.LogoutDto;
import com.example.orchestrator.service.AuthService;
import com.example.orchestrator.util.CookieUtil;
import com.example.orchestrator.util.GrpcResponseHelper;
import com.example.orchestrator.util.JsonUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import com.example.grpc.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    private final AuthService authService;
    private final GrpcResponseHelper grpcResponseHelper;
    private final JsonUtil jsonUtil;
    private final WebSocketController webSocketController;
    private final Validator validator;

    @Autowired
    public CustomLogoutHandler(AuthService authService, GrpcResponseHelper grpcResponseHelper, JsonUtil jsonUtil, WebSocketController webSocketController, Validator validator) {
        this.authService = authService;
        this.grpcResponseHelper = grpcResponseHelper;
        this.jsonUtil = jsonUtil;
        this.webSocketController = webSocketController;
        this.validator = validator;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LogoutDto logoutDto = parseLogoutDto(request);
        Set<ConstraintViolation<LogoutDto>> violations = validator.validate(logoutDto);
        if (!violations.isEmpty()) {
            sendValidationErrorResponse(response, violations);
            return;
        }
        Response logoutResponse = authService.logout(logoutDto);
        invalidateCookies(request, response);
        webSocketController.sendLogoutMessage(logoutDto.getUserId());
        request.getSession().invalidate();
        sendJsonResponse(response, grpcResponseHelper.createJsonResponse(logoutResponse));
    }

    private LogoutDto parseLogoutDto(HttpServletRequest request) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return jsonUtil.parseJson(sb.toString(), LogoutDto.class);
        } catch (IOException e) {
            return null;
        }
    }


    private void invalidateCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TokenConstants.ACCESS_TOKEN.equals(cookie.getName())) {
                    CookieUtil.invalidateTokenCookie(response);
                }
            }
        }
    }

    private void sendJsonResponse(HttpServletResponse response, ResponseEntity<String> entity) {
        try {
            response.setStatus(entity.getStatusCode().value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            if (entity.getBody() != null) {
                response.getWriter().write(entity.getBody());
            }

        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void sendValidationErrorResponse(HttpServletResponse response, Set<ConstraintViolation<LogoutDto>> violations) {
        try {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            StringBuilder errorMessages = new StringBuilder("{\"errors\":[");
            for (ConstraintViolation<LogoutDto> violation : violations) {
                errorMessages.append("{\"field\":\"").append(violation.getPropertyPath())
                        .append("\",\"message\":\"").append(violation.getMessage()).append("\"},");
            }
            if (errorMessages.charAt(errorMessages.length() - 1) == ',') {
                errorMessages.deleteCharAt(errorMessages.length() - 1);
            }
            errorMessages.append("]}");

            response.getWriter().write(errorMessages.toString());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}


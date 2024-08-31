package com.example.orchestrator.controller;

import com.example.grpc.Response;
import com.example.orchestrator.dto.AuthStatusUserDto;
import com.example.orchestrator.dto.request.auth.LoginDto;
import com.example.orchestrator.dto.request.auth.LogoutDto;
import com.example.orchestrator.service.AuthService;
import com.example.orchestrator.service.UserService;
import com.example.orchestrator.util.CookieUtil;
import com.example.orchestrator.util.GrpcResponseHelper;
import com.example.orchestrator.util.JsonUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final AuthService authService;
    private final UserService userService;
    private final GrpcResponseHelper grpcResponseHelper;
    private final JsonUtil jsonUtil;
    private final WebSocketController webSocketController;

    @Autowired
    public AuthController(AuthService authService, UserService userService, GrpcResponseHelper grpcResponseHelper, JsonUtil jsonUtil, WebSocketController webSocketController) {
        this.authService = authService;
        this.userService = userService;
        this.grpcResponseHelper = grpcResponseHelper;
        this.jsonUtil = jsonUtil;
        this.webSocketController = webSocketController;
    }

    @GetMapping(value = "/status")
    public ResponseEntity<Map<String, Object>> getAuthStatus() {
        Map<String, Object> response = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Response userResponse = userService.getUserByUsername(userDetails.getUsername());
            Map<String, Object> userResponseMap = jsonUtil.getMapByKey(userResponse.getResult(), "results");
            String username = (String) userResponseMap.get("username");
            Integer id = (Integer) userResponseMap.get("id");
            Long userId = id != null ? id.longValue() : null;

            AuthStatusUserDto authStatusUserDto = new AuthStatusUserDto(
                userId,
                username,
                userDetails.getAuthorities(),
                userDetails.isAccountNonExpired(),
                userDetails.isAccountNonLocked(),
                userDetails.isCredentialsNonExpired(),
                userDetails.isEnabled()
            );

            response.put("isAuthenticated", true);
            response.put("user", authStatusUserDto);
        } else {
            response.put("isAuthenticated", false);
            response.put("user", null);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse) {
        Response response = authService.login(loginDto);
        boolean authenticated = Boolean.parseBoolean(jsonUtil.getValueByKey(response.getResult(), "authenticated"));

        if (authenticated) {
            String accessToken = jsonUtil.getValueByKey(response.getResult(), "accessToken");
            CookieUtil.addTokenCookie(httpServletResponse, accessToken);
        }

        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/logout", consumes = "application/json")
    public ResponseEntity<String> logout(@Valid @RequestBody LogoutDto logoutDto, HttpServletResponse httpServletResponse) {
        Response response = authService.logout(logoutDto);
        CookieUtil.invalidateTokenCookie(httpServletResponse);
        webSocketController.sendLogoutMessage(logoutDto.getUserId());
        return grpcResponseHelper.createJsonResponse(response);
    }
}

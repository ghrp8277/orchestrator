package com.example.orchestrator.controller;

import com.example.grpc.Response;
import com.example.orchestrator.constants.TokenConstants;
import com.example.orchestrator.dto.LoginDto;
import com.example.orchestrator.dto.LogoutDto;
import com.example.orchestrator.service.AuthService;
import com.example.orchestrator.util.GrpcResponseHelper;
import com.example.orchestrator.util.JsonUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseCookie;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final AuthService authService;
    private final GrpcResponseHelper grpcResponseHelper;
    private final JsonUtil jsonUtil;

    @Autowired
    public AuthController(AuthService authService, GrpcResponseHelper grpcResponseHelper, JsonUtil jsonUtil) {
        this.authService = authService;
        this.grpcResponseHelper = grpcResponseHelper;
        this.jsonUtil = jsonUtil;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse) {
        Response response = authService.login(loginDto);
        boolean authenticated = Boolean.parseBoolean(jsonUtil.getValueByKey(response.getResult(), "authenticated"));

        if (authenticated) {
            String accessToken = jsonUtil.getValueByKey(response.getResult(), "accessToken");
            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(TokenConstants.COOKIE_MAX_AGE)
                .sameSite("Strict")
                .build();

            httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }

        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/logout", consumes = "application/json")
    public ResponseEntity<String> logout(@Valid @RequestBody LogoutDto logoutDto, HttpServletResponse httpServletResponse) {
        Response response = authService.logout(logoutDto);

        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(TokenConstants.COOKIE_MIN_AGE)
                .sameSite("Strict")
                .build();

        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return grpcResponseHelper.createJsonResponse(response);
    }
}

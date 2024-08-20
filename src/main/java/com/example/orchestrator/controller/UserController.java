package com.example.orchestrator.controller;
import com.example.orchestrator.constants.HttpConstants;
import com.example.orchestrator.dto.request.user.PasswordUpdateDto;
import com.example.orchestrator.dto.request.social.UpdateProfileDto;
import com.example.orchestrator.dto.request.user.UserDto;
import com.example.orchestrator.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.grpc.*;
import com.example.orchestrator.util.GrpcResponseHelper;
import org.springframework.web.multipart.MultipartFile;
import com.example.orchestrator.util.CookieUtil;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final GrpcResponseHelper grpcResponseHelper;
    private final WebSocketController webSocketController;

    @Autowired
    public UserController(UserService userService, GrpcResponseHelper grpcResponseHelper, WebSocketController webSocketController) {
        this.userService = userService;
        this.grpcResponseHelper = grpcResponseHelper;
        this.webSocketController = webSocketController;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> getUserById(
            @RequestHeader("user-id") @NotNull Long userId
    ) {
        Response response = userService.getUserById(userId);
        
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(HttpConstants.CACHE_MAX_AGE_SECONDS, TimeUnit.SECONDS))
                .body(grpcResponseHelper.createJsonResponse(response).getBody());
    }

    @GetMapping("/check-username")
    public ResponseEntity<String> checkUsername(
            @Valid
            @RequestParam
            @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters long")
            String username
    ) {
        Response response = userService.checkUsername(username);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<String> registerUser(
            @Valid
            @RequestPart("user") UserDto userDto,
            @RequestPart("profileImage") MultipartFile profileImage
    ) {
        Response response = userService.registerUser(userDto, profileImage);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/change-password", consumes = "application/json")
    public ResponseEntity<String> updatePassword(
            @Valid @RequestBody
            PasswordUpdateDto passwordUpdateDto,
            HttpServletResponse httpServletResponse
    ) {
        Response response = userService.updatePassword(passwordUpdateDto);
        CookieUtil.invalidateTokenCookie(httpServletResponse);
        CookieUtil.redirectTo(httpServletResponse, "/login");
        webSocketController.sendLogoutMessage(passwordUpdateDto.getUserId());
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/update-profile", consumes = "multipart/form-data")
    public ResponseEntity<String> updateProfile(
            @Valid
            @RequestPart("user") UpdateProfileDto updateProfileDto,
            @RequestPart("profileImage") MultipartFile profileImage
    ) {
        Response response = userService.updateProfile(updateProfileDto, profileImage);
        return grpcResponseHelper.createJsonResponse(response);
    }
}
package com.example.orchestrator.controller;
import com.example.orchestrator.dto.PasswordUpdateDto;
import com.example.orchestrator.dto.UpdateProfileDto;
import com.example.orchestrator.dto.UserDto;
import com.example.orchestrator.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.grpc.*;
import com.example.orchestrator.util.GrpcResponseHelper;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final GrpcResponseHelper grpcResponseHelper;

    @Autowired
    public UserController(UserService userService, GrpcResponseHelper grpcResponseHelper) {
        this.userService = userService;
        this.grpcResponseHelper = grpcResponseHelper;
    }

    @GetMapping("/check-username")
    public ResponseEntity<String> checkUsername(@Valid @RequestParam String username) {
        Response response = userService.checkUsername(username);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<String> registerUser(@Valid @RequestPart("user") UserDto userDto,
                                               @RequestPart("profileImage") MultipartFile profileImage) {
        Response response = userService.registerUser(userDto, profileImage);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/change-password", consumes = "application/json")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordUpdateDto passwordUpdateDto) {
        Response response = userService.updatePassword(passwordUpdateDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/update-profile", consumes = "multipart/form-data")
    public ResponseEntity<String> updateProfile(@Valid @RequestPart("user") UpdateProfileDto updateProfileDto,
                                                @RequestPart("profileImage") MultipartFile profileImage) {
        Response response = userService.updateProfile(updateProfileDto, profileImage);
        return grpcResponseHelper.createJsonResponse(response);
    }
}
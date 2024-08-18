package com.example.orchestrator.service;

import com.example.grpc.*;
import com.example.orchestrator.dto.*;
import com.example.orchestrator.dto.request.social.UpdateProfileDto;
import com.example.orchestrator.dto.request.user.FindUserDto;
import com.example.orchestrator.dto.request.user.PasswordUpdateDto;
import com.example.orchestrator.dto.request.user.UserDto;
import com.example.orchestrator.service.grpc.AuthGrpcService;
import com.example.orchestrator.service.grpc.SocialGrpcService;
import com.example.orchestrator.service.grpc.UserGrpcService;
import com.example.orchestrator.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserGrpcService userGrpcService;
    private final AuthGrpcService authGrpcService;
    private final SocialGrpcService socialGrpcService;
    private final FileUploadService fileUploadService;
    private final JsonUtil jsonUtil;

    public Response registerUser(UserDto userDto, MultipartFile profileImage) {
        ImageDto imageDto = fileUploadService.storeFile(profileImage);

        Image image = Image.newBuilder()
            .setPath(imageDto.getUploadPath())
            .setOriginalFilename(imageDto.getOriginalFilename())
            .setFileExtension(imageDto.getFileExtension())
            .setDescription("Profile image for user " + userDto.getUsername())
            .build();

        Profile profile = Profile.newBuilder()
            .setGreeting(userDto.getProfile().getGreeting())
            .setImage(image)
            .build();

        RegisterUserRequest request = RegisterUserRequest.newBuilder()
            .setUsername(userDto.getUsername())
            .setPassword(userDto.getPassword())
            .setEmail(userDto.getEmail())
            .setProfile(profile)
            .build();

        Response response = userGrpcService.registerUser(request);

        if (!responseHasError(response)) {
            String result = response.getResult();
            Map<String, Object> results = jsonUtil.getMapByKey(result, "user");
            long id = ((Number) results.get("id")).longValue();

            CreateUserSyncInfoRequest syncInfoRequest = CreateUserSyncInfoRequest.newBuilder()
                    .setUserId(id)
                    .setUsername(userDto.getUsername())
                    .setActive(true)
                    .build();

            socialGrpcService.createUserSyncInfo(syncInfoRequest);
        }

        return response;
    }

    public Response checkUsername(String username) {
        CheckUsernameRequest request = CheckUsernameRequest.newBuilder()
                .setUsername(username)
                .build();
        return userGrpcService.checkUsername(request);
    }

    public Response updatePassword(PasswordUpdateDto passwordUpdateDto) {
        UpdatePasswordRequest request = UpdatePasswordRequest.newBuilder()
                .setUserId(passwordUpdateDto.getUserId())
                .setCurrentPassword(passwordUpdateDto.getCurrentPassword())
                .setNewPassword(passwordUpdateDto.getNewPassword())
                .build();

        LogoutRequest logoutRequest = LogoutRequest.newBuilder()
                .setUserId(passwordUpdateDto.getUserId())
                .build();

        Response response = userGrpcService.updatePassword(request);
        authGrpcService.logout(logoutRequest);

        return response;
    }

    public Response findUser(FindUserDto findUserDto) {
        FindUserRequest request = FindUserRequest.newBuilder()
                .setUserId(findUserDto.getUserId())
                .build();

        return userGrpcService.findUser(request);
    }

    public Response authenticateUser(String username, String password) {
        AuthenticateUserRequest request = AuthenticateUserRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();
        return userGrpcService.authenticateUser(request);
    }

    public Response updateProfile(UpdateProfileDto updateProfileDto, MultipartFile profileImage) {
        ImageDto imageDto = fileUploadService.storeFile(profileImage);

        Image image = Image.newBuilder()
            .setPath(imageDto.getUploadPath())
            .setOriginalFilename(imageDto.getOriginalFilename())
            .setFileExtension(imageDto.getFileExtension())
            .setDescription("Updated profile image for user " + updateProfileDto.getUserId())
            .build();

        Profile profile = Profile.newBuilder()
            .setGreeting(updateProfileDto.getGreeting())
            .setImage(image)
            .build();

        UpdateProfileRequest request = UpdateProfileRequest.newBuilder()
            .setUserId(updateProfileDto.getUserId())
            .setProfile(profile)
            .build();

        return userGrpcService.updateProfile(request);
    }

    private boolean responseHasError(Response response) {
        String result = response.getResult();
        return result.contains("\"error\"");
    }
}

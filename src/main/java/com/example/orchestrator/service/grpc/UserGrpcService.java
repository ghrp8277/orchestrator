package com.example.orchestrator.service.grpc;
import com.example.grpc.*;

public interface UserGrpcService {
    Response getUserById (GetUserByIdRequest getUserByIdRequest);
    Response getUserByUsername (GetUserByUsernameRequest getUserByUsernameRequest);
    Response registerUser(RegisterUserRequest registerUserRequest);
    Response checkUsername(CheckUsernameRequest checkUsernameRequest);
    Response authenticateUser(AuthenticateUserRequest authenticateUserRequest);
    Response updatePassword(UpdatePasswordRequest updatePasswordRequest);
    Response findUser(FindUserRequest findUserRequest);
    Response updateProfile(UpdateProfileRequest updateProfileRequest);
}

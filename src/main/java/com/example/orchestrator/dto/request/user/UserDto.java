package com.example.orchestrator.dto.request.user;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters long")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
        message = "Email should be valid"
    )
    private String email;

    @NotNull(message = "Profile is required")
    private ProfileDto profile;
}

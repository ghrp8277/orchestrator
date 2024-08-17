package com.example.orchestrator.dto.request.email;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailVerifyCodeDto {
    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
        message = "Email should be valid"
    )
    private String email;

    @NotBlank(message = "Verification code is required")
    @Size(min = 6, max = 6, message = "Verification code must be exactly 6 characters long")
    private String code;
}

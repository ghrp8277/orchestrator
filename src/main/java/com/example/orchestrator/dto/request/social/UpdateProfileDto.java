package com.example.orchestrator.dto.request.social;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateProfileDto {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Greeting is required")
    private String greeting;
}
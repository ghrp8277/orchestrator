package com.example.orchestrator.dto.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileDto {
    @NotNull(message = "Greeting is required")
    @Size(max = 255, message = "Greeting must be less than 255 characters")
    private String greeting;
}

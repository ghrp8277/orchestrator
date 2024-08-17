package com.example.orchestrator.dto.request.email;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class EmailSendDto {
    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
        message = "Email should be valid"
    )
    private String email;
}

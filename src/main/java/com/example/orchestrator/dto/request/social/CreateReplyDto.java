package com.example.orchestrator.dto.request.social;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateReplyDto {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Username is required")
    private String username;

    @NotNull(message = "Content is required")
    private String content;
}

package com.example.orchestrator.dto.request.social;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeleteCommentDto {
    @NotNull(message = "User ID is required")
    private Long userId;
}

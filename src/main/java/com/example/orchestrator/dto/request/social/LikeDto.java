package com.example.orchestrator.dto.request.social;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeDto {
    @NotNull
    private Long userId;
}

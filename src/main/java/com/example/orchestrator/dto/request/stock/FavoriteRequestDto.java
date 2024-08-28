package com.example.orchestrator.dto.request.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRequestDto {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Stock code is required")
    private String stockCode;
}

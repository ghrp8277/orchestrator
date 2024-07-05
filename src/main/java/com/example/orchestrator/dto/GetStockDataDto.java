package com.example.orchestrator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetStockDataDto {
    @NotBlank(message = "Timeframe is required")
    @Pattern(regexp = "^(1month|1year|3years|5years)$", message = "Invalid timeframe. Allowed values are: 1month, 1year, 3years, 5years")
    private String timeframe;
}

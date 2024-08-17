package com.example.orchestrator.dto.request.common;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequestDto {
    @NotNull(message = "Page number is required")
    @Min(value = 0, message = "Page number cannot be less than 0")
    private int page;

    @NotNull(message = "PageSize number is required")
    @Min(value = 1, message = "Page size must be at least 1")
    private int pageSize;
}

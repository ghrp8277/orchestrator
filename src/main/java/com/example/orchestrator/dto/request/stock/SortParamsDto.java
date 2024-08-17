package com.example.orchestrator.dto.request.stock;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortParamsDto {
    @NotNull(message = "Sort parameter is required")
    @Pattern(
            regexp = "^(name|code|transactionAmount|priceChangeRate),(asc|desc)$",
            message = "Invalid sort format. Use 'field,order' where order is 'asc' or 'desc'"
    )
    private String sort = "name,asc";
}
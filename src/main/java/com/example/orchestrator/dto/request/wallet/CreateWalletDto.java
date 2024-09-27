package com.example.orchestrator.dto.request.wallet;

import com.example.orchestrator.enums.Currency;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateWalletDto {

    @NotNull(message = "User ID is required")
    private int userId;

    @NotNull(message = "Currency is required")
    private Currency currency;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    private String password;
}

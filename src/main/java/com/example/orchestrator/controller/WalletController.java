package com.example.orchestrator.controller;

import com.example.orchestrator.dto.request.wallet.CreateWalletDto;
import com.example.orchestrator.dto.request.wallet.DepositDto;
import com.example.orchestrator.dto.request.wallet.WithdrawDto;
import com.example.orchestrator.service.WalletService;
import com.example.orchestrator.util.GrpcResponseHelper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.grpc.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final WalletService walletService;
    private final GrpcResponseHelper grpcResponseHelper;

    @Autowired
    public WalletController(WalletService walletService, GrpcResponseHelper grpcResponseHelper) {
        this.walletService = walletService;
        this.grpcResponseHelper = grpcResponseHelper;
    }

    @GetMapping(value = "/:id")
    public ResponseEntity<String> getWallet() {
        Response response = walletService.getWallet();
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<String> createWallet(
            @Valid
            @RequestBody
            CreateWalletDto createWalletDto
    ) {
        Response response = walletService.createWallet(createWalletDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/deposit", consumes = "application/json")
    public ResponseEntity<String> deposit(
            @Valid
            @RequestBody
            DepositDto depositDto
    ) {
        Response response = walletService.deposit(depositDto);
        return grpcResponseHelper.createJsonResponse(response);
    }

    @PostMapping(value = "/withdraw", consumes = "application/json")
    public ResponseEntity<String> withdraw(
            @Valid
            @RequestBody
            WithdrawDto withdrawDto
    ) {
        Response response = walletService.withdraw(withdrawDto);
        return grpcResponseHelper.createJsonResponse(response);
    }
}

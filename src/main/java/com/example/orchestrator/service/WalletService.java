package com.example.orchestrator.service;

import com.example.orchestrator.dto.request.wallet.CreateWalletDto;
import com.example.orchestrator.dto.request.wallet.DepositDto;
import com.example.orchestrator.dto.request.wallet.WithdrawDto;
import com.example.orchestrator.service.grpc.WalletGrpcService;
import com.example.orchestrator.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.grpc.*;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletGrpcService walletGrpcService;
    private final JsonUtil jsonUtil;

    public Response createWallet(CreateWalletDto createWalletDto) {
        CreateWalletRequest request = CreateWalletRequest.newBuilder()
                .setUserId(createWalletDto.getUserId())
                .setCurrency(createWalletDto.getCurrency().toValue())
                .setPassword(createWalletDto.getPassword())
                .setIsDefault(true)
                .build();

        return walletGrpcService.createWallet(request);
    }

    public Response deposit(DepositDto depositDto) {
        DepositRequest request = DepositRequest.newBuilder()
                .setUserId(depositDto.getUserId())
                .setAmount(depositDto.getAmount())
                .build();

        return walletGrpcService.deposit(request);
    }

    public Response withdraw(WithdrawDto withdrawDto) {
        WithdrawRequest request = WithdrawRequest.newBuilder()
                .setUserId(withdrawDto.getUserId())
                .setAmount(withdrawDto.getAmount())
                .build();

        return walletGrpcService.withdraw(request);
    }
}

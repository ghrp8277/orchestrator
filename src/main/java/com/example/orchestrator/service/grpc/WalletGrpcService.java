package com.example.orchestrator.service.grpc;
import com.example.grpc.*;

public interface WalletGrpcService {
    Response createWallet(CreateWalletRequest createWalletRequest);
    Response deposit(DepositRequest depositRequest);
    Response withdraw(WithdrawRequest withdrawRequest);
}
